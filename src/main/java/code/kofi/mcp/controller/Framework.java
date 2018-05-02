package code.kofi.mcp.controller;

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

    @Autowired
    private BasicValidationTask basicValidationTask;

    private ForkJoinPool pool = new ForkJoinPool( cores );

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
                    () -> {

                        try {

                            System.out.println("##############");
                            List<Pair<Integer,List<String>>> result = pool.submit( this.basicValidationTask.setCars( cars ) ).get();
                            System.out.println("result: " + result);
                            System.out.println("##############");

                            this.basicValidationTask.clearCars();

                            return result;

                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                );

        }

        return null;

    }
}