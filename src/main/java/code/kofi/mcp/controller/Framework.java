package code.kofi.mcp.controller;

import code.kofi.mcp.conifg.ValidationFramework;
import code.kofi.mcp.dto.Car;
import code.kofi.mcp.service.task.BasicValidationTask;
import code.kofi.mcp.service.task.CustomValidationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

import static code.kofi.mcp.util.Base.*;

@RestController
@RequestMapping("/api")
public class Framework {

    private final static int cores = Runtime.getRuntime().availableProcessors();

    private ForkJoinPool pool = new ForkJoinPool( cores );

    @Autowired
    private ValidationFramework framework;

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/")
    public String description(){
        return "This is the Framework Controller";
    }

    @PostMapping("/validateCar")
    public Future<Map<Integer,List<String>>> validateCar(@RequestParam("file") MultipartFile file) {

        Car[] cars = buildCarListFromFile(file);

        if( cars != null ){

            CompletableFuture<Map<Integer,List<String>>> future =  CompletableFuture
                .supplyAsync(
                    () -> pool.invoke( new BasicValidationTask( this.framework ).setCars( cars ) )
                ).handleAsync(
                            (postBasicMap, throwable) -> {

                                if( postBasicMap.size() > 0 && !this.framework.getCarValidation().getReturnOnStepError() ) {

                                    Set<Integer> keys = postBasicMap.keySet();

                                    Car[]  carsThatPassedBasicValidation = Arrays.stream( cars )
                                            .filter( car -> !keys.contains( car.getRow() ) )
                                            .toArray(Car[]::new);

                                    Map<Integer,List<String>> postCustomMap = pool.invoke( new CustomValidationTask( this.framework ).setCars( carsThatPassedBasicValidation ) );
                                    postCustomMap.putAll( postBasicMap );

                                    return postCustomMap;

                                }

                                return postBasicMap;

                            }
                    );

            return future;

        }

        return null;

    }
}