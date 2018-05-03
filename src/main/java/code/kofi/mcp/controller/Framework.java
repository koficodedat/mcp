package code.kofi.mcp.controller;

import code.kofi.mcp.conifg.ValidationFramework;
import code.kofi.mcp.dto.Car;
import code.kofi.mcp.service.BasicValidationTask;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.*;

import static code.kofi.mcp.util.Base.buildCarListFromFile;

@RestController
@RequestMapping("/framework")
public class Framework {

    private final static int cores = 4;

    private ForkJoinPool pool = new ForkJoinPool( cores );

    @Autowired
    private ValidationFramework framework;

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/")
    public String description(){
        return "This is the Framework Controller";
    }

    @PostMapping("/validateCar")
    public Future<List<Pair<Integer,List<String>>>> validateCar(@RequestParam("file") MultipartFile file) {

        Car[] cars = buildCarListFromFile(file);

        if( cars != null ){

            return CompletableFuture
                .supplyAsync(
                    () -> pool.invoke( new BasicValidationTask( this.framework ).setCars( cars ) )
                );

        }

        return null;

    }
}