package code.kofi.mcp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import code.kofi.mcp.conifg.ValidationFramework;
import code.kofi.mcp.dto.Car;
import code.kofi.mcp.dto.Command;
import code.kofi.mcp.factory.TestBench;
import code.kofi.mcp.service.ValidateElectric;
import code.kofi.mcp.service.ValidateMake;
import code.kofi.mcp.service.ValidateModel;
import code.kofi.mcp.service.ValidateRange;
import code.kofi.mcp.service.ValidateStreetSave;
import code.kofi.mcp.service.ValidateTrim;
import code.kofi.mcp.service.ValidateType;
import code.kofi.mcp.service.ValidateYear;
import code.kofi.mcp.util.Base;

@RestController
@RequestMapping("/framework")
public class Framework {

    final static int cores = 4;

    @Autowired
    private ValidationFramework framework;
    
    @Autowired
    private ValidateType validateType;
    @Autowired
    private ValidateMake validateMake;
    @Autowired
    private ValidateModel validateModel;
    @Autowired
    private ValidateTrim validateTrim;
    @Autowired
    private ValidateYear validateYear;
    @Autowired
    private ValidateStreetSave validateStreetSave;
    @Autowired
    private ValidateElectric validateElectric;
    @Autowired
    private ValidateRange validateRange;

    @GetMapping("/")
    public String description(){
        return "This is the Framework Controller";
    }

    @GetMapping("/stringifyValidation")
    public String stringifyFrameworkContent(){
        return this.framework.toString();
    }

    // @PostMapping("/validateCar")
    // public Future<Map<Integer,List<String>>> validateCar(@RequestParam("file") MultipartFile file) throws InterruptedException, ExecutionException{

    //     // List<Car> cars = TestBench.getCars();
    //     List<Car> cars = Base.buildCarListFromFile(file);

    //     if( cars != null ){

    //         CompletableFuture< Map<Integer,List<String>> > future = CompletableFuture
    //             .supplyAsync(
    //                 () -> {

    //                     Map<Integer,List<String>> map = new HashMap<>(); 
    //                     ForkJoinPool pool = new ForkJoinPool( cores );

    //                     pool.submit(
    //                         () -> cars
    //                             .parallelStream()
    //                             .forEach(
    //                                 car -> 
    //                                 {

    //                                     System.out.println("CAR: " + car);

    //                                     framework
    //                                         .getCarValidation()
    //                                         .getCommands()
    //                                         .parallelStream()
    //                                         .forEach(
    //                                             command -> {
    //                                                 switch( command.getDescription() ){

    //                                                     case "Type":
    //                                                         validateType.executeBasic( command.getDescription(), car.getRow(), car.getType(), command.getValidations().getBasic(), map );
    //                                                         break;
    //                                                     case "Make":
    //                                                         validateMake.executeBasic( command.getDescription(), car.getRow(), car.getMake(), command.getValidations().getBasic(), map );
    //                                                         break;
    //                                                     case "Model":
    //                                                         validateModel.executeBasic( command.getDescription(), car.getRow(), car.getModel(), command.getValidations().getBasic(), map );
    //                                                         break;
    //                                                     case "Trim":
    //                                                         validateTrim.executeBasic( command.getDescription(), car.getRow(), car.getTrim(), command.getValidations().getBasic(), map );
    //                                                         break;
    //                                                     case "Year":
    //                                                         validateYear.executeBasic( command.getDescription(), car.getRow(), car.getYear(), command.getValidations().getBasic(), map );
    //                                                         break;
    //                                                     case "StreetSave":
    //                                                         validateStreetSave.executeBasic( command.getDescription(), car.getRow(),car.getIsStreetSave(), command.getValidations().getBasic(), map );
    //                                                         break;
    //                                                     case "Electric":
    //                                                         validateElectric.executeBasic( command.getDescription(), car.getRow(), car.getIsElectric(), command.getValidations().getBasic(), map );
    //                                                         break;
    //                                                     case "Range":
    //                                                         validateRange.executeBasic( command.getDescription(), car.getRow(), car.getRange(), command.getValidations().getBasic(), map );
    //                                                         break;
    //                                                     default:
    //                                                         break;
            
    //                                                 }

    //                                                 System.out.println("MAP--: " + map);
    //                                             }
    //                                         );
    //                                 }
    //                             )
    //                     );

    //                     System.out.println("MAP: " + map);

    //                     return map;
                        
    //                 }
    //             );

    //         // Map<Integer,List<String>> map = new HashMap<>(); 

    //         // ForkJoinPool pool = new ForkJoinPool( cores );

    //         // pool.submit(
    //         //     () -> cars
    //         //         .parallelStream()
    //         //         .forEach(
    //         //             ( car ) ->  {
    //         //                 // query.bind(index[0]++, car)
    //         //                 // basicValidation.execute( car );
    //         //                 // validateType.executeBasic( car.getType() );

    //         //                 // Stream<Command> parallelCommandStream =  framework
    //         //                 //     .getCarValidation()
    //         //                 //     .getCommands()
    //         //                 //     .stream()
    //         //                 //     .filter(
    //         //                 //         ( command ) -> {
    //         //                 //             return command.getInParallel();
    //         //                 //         }
    //         //                 //     );

    //         //                 // Stream<Command> nonParallelCommandStream =  framework
    //         //                 //     .getCarValidation()
    //         //                 //     .getCommands()
    //         //                 //     .stream()
    //         //                 //     .filter(
    //         //                 //         ( command ) -> {
    //         //                 //             return !command.getInParallel();
    //         //                 //         }
    //         //                 //     );

    //         //                 // parallelCommandStream
    //         //                 //     .parallel()
    //         //                 //     .forEach(
    //         //                 //         ( stream ) -> {

    //         //                 //         }
    //         //                 //     );

    //         //                 framework
    //         //                     .getCarValidation()
    //         //                     .getCommands()
    //         //                     .parallelStream()
    //         //                     .forEach(
    //         //                         ( command ) -> {
                                        
    //         //                             switch( command.getDescription() ){

    //         //                                 case "Type":
    //         //                                     validateType.executeBasic( command.getDescription(), car.getRow(), car.getType(), command.getValidations().getBasic(), map );
    //         //                                     break;
    //         //                                 case "Make":
    //         //                                     validateMake.executeBasic( command.getDescription(), car.getRow(), car.getMake(), command.getValidations().getBasic(), map );
    //         //                                     break;
    //         //                                 case "Model":
    //         //                                     validateModel.executeBasic( command.getDescription(), car.getRow(), car.getModel(), command.getValidations().getBasic(), map );
    //         //                                     break;
    //         //                                 case "Trim":
    //         //                                     validateTrim.executeBasic( command.getDescription(), car.getRow(), car.getTrim(), command.getValidations().getBasic(), map );
    //         //                                     break;
    //         //                                 case "Year":
    //         //                                     validateYear.executeBasic( command.getDescription(), car.getRow(), car.getYear(), command.getValidations().getBasic(), map );
    //         //                                     break;
    //         //                                 case "StreetSave":
    //         //                                     validateStreetSave.executeBasic( command.getDescription(), car.getRow(),car.getIsStreetSave(), command.getValidations().getBasic(), map );
    //         //                                     break;
    //         //                                 case "Electric":
    //         //                                     validateElectric.executeBasic( command.getDescription(), car.getRow(), car.getIsElectric(), command.getValidations().getBasic(), map );
    //         //                                     break;
    //         //                                 case "Range":
    //         //                                     validateRange.executeBasic( command.getDescription(), car.getRow(), car.getRange(), command.getValidations().getBasic(), map );
    //         //                                     break;
    //         //                                 default:
    //         //                                     break;

    //         //                             }

    //         //                         }
    //         //                     );
    //         //             }
    //         //         )
    //         // );

    //         // return map;

    //         System.out.println("MAP 2: " + future);

    //         CompletableFuture<String> future2 = future.thenApply(s -> { System.out.println(s); } );

    //         return future;
        
    //     }
        
    //     return null;
    // }


    @PostMapping("/validateCar")
    public Future<Map<Integer,List<String>>> validateCar(@RequestParam("file") MultipartFile file) throws InterruptedException, ExecutionException{

        List<Car> cars = Base.buildCarListFromFile(file);

        if( cars != null ){

            CompletableFuture< Map<Integer,List<String>> > future = CompletableFuture
                .supplyAsync(
                    () -> {

                        ForkJoinPool pool = new ForkJoinPool( cores );

                        ForkJoinTask<Map<Integer,List<String>>> mapTask = pool
                            .submit(
                                () -> {

                                    Map<Integer,List<String>> map = new HashMap<>();
                                    
                                    cars
                                    .parallelStream()
                                    .forEach(
                                        car -> {
    
                                            framework
                                                .getCarValidation()
                                                .getCommands()
                                                .parallelStream()
                                                .forEach(
                                                    command -> {
                                                        
                                                        switch( command.getDescription() ){
    
                                                            case "Type":
                                                                validateType.executeBasic( command.getDescription(), car.getRow(), car.getType(), command.getValidations().getBasic(), map );
                                                                break;
                                                            case "Make":
                                                                validateMake.executeBasic( command.getDescription(), car.getRow(), car.getMake(), command.getValidations().getBasic(), map );
                                                                break;
                                                            case "Model":
                                                                validateModel.executeBasic( command.getDescription(), car.getRow(), car.getModel(), command.getValidations().getBasic(), map );
                                                                break;
                                                            case "Trim":
                                                                validateTrim.executeBasic( command.getDescription(), car.getRow(), car.getTrim(), command.getValidations().getBasic(), map );
                                                                break;
                                                            case "Year":
                                                                validateYear.executeBasic( command.getDescription(), car.getRow(), car.getYear(), command.getValidations().getBasic(), map );
                                                                break;
                                                            case "StreetSave":
                                                                validateStreetSave.executeBasic( command.getDescription(), car.getRow(),car.getIsStreetSave(), command.getValidations().getBasic(), map );
                                                                break;
                                                            case "Electric":
                                                                validateElectric.executeBasic( command.getDescription(), car.getRow(), car.getIsElectric(), command.getValidations().getBasic(), map );
                                                                break;
                                                            case "Range":
                                                                validateRange.executeBasic( command.getDescription(), car.getRow(), car.getRange(), command.getValidations().getBasic(), map );
                                                                break;
                                                            default:
                                                                break;
                
                                                        }
    
                                                        System.out.println(car.getRow() + "-" + command.getDescription() + "-" + map);
                                                    }
                                                );
                                        }
                                    );
                                    
                                    return map;
                                }
                            );

                        try {
							return mapTask.get();
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
                        }
                        
                        return null;
                        
                    }
                );

            return future;

        }

        return null;

    }
}