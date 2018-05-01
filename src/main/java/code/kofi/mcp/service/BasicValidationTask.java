package code.kofi.mcp.service;

import code.kofi.mcp.conifg.ValidationFramework;
import code.kofi.mcp.dto.Car;
import javafx.util.Pair;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collector;

@Service
@Configurable
@NoArgsConstructor
public class BasicValidationTask extends RecursiveTask< List<Pair<Integer,List<String>>> > {

    private static final int THRESHOLD = 10;

    private Car[] cars;

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

    @Autowired
    public BasicValidationTask(Car[] cars){
        this.cars = cars;
    }

    @Override
    protected List<Pair<Integer,List<String>>> compute() {

        if( this.cars.length > THRESHOLD ){
            return ForkJoinTask
                    .invokeAll( createSubTask() )
                    .stream()
                    .map( task -> task.process( task.cars ) )
                    .collect( Collector.of( ArrayList::new, List::addAll, (left, right) -> { left.addAll(right); return left; } ) );
        }

        return this.process( this.cars );
    }

    private Collection<BasicValidationTask> createSubTask() {
        List<BasicValidationTask> dividedTask = new ArrayList<>();

        dividedTask.add( new BasicValidationTask( Arrays.copyOfRange( this.cars, 0, this.cars.length / 2 ) ) );
        dividedTask.add( new BasicValidationTask( Arrays.copyOfRange( this.cars, this.cars.length / 2, this.cars.length ) ) );

        return dividedTask;
    }

    private List<Pair<Integer,List<String>>> process(Car[] cars){


        List<Pair<Integer,List<String>>> pairs = new ArrayList<>();
        List<List<Callable<String>>> rootCallables = new ArrayList<>();

        Arrays.stream( cars )
                .forEachOrdered(
                        car -> {
                            List<Callable<String>> callables = new ArrayList<>();

                            System.out.println("#########" + this.framework + "#################");

                            this.framework.getCarValidation().getCommands()
                                    .forEach(
                                            command -> {
                                                switch (command.getDescription()){
                                                    case "Type":
                                                        callables.add( () -> validateType.executeBasic( command.getDescription(), car.getType(), command.getValidations().getBasic() ) );
                                                        break;
                                                    case "Make":
                                                        callables.add( () -> validateMake.executeBasic( command.getDescription(), car.getMake(), command.getValidations().getBasic() ) );
                                                        break;
                                                    case "Model":
                                                        callables.add( () -> validateModel.executeBasic( command.getDescription(), car.getModel(), command.getValidations().getBasic() ) );
                                                        break;
                                                    case "Trim":
                                                        callables.add( () -> validateTrim.executeBasic( command.getDescription(), car.getTrim(), command.getValidations().getBasic() ) );
                                                        break;
                                                    case "Year":
                                                        callables.add( () -> validateYear.executeBasic( command.getDescription(), car.getYear(), command.getValidations().getBasic() ) );
                                                        break;
                                                    case "StreetSave":
                                                        callables.add( () -> validateStreetSave.executeBasic( command.getDescription(), car.getIsStreetSave(), command.getValidations().getBasic() ) );
                                                        break;
                                                    case "Electric":
                                                        callables.add( () -> validateElectric.executeBasic( command.getDescription(), car.getIsElectric(), command.getValidations().getBasic() ) );
                                                        break;
                                                    case "Range":
                                                        callables.add( () -> validateRange.executeBasic( command.getDescription(), car.getRange(), command.getValidations().getBasic() ) );
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            }
                                    );

                            rootCallables.add(callables);
                        }
                );

        rootCallables
                .forEach(
                        callables -> {
                            List<String> strings = new ArrayList<>();
                            pairs.add( new Pair<>( pairs.size() + 1, strings ) );

                            callables.forEach( callable -> callValidation(strings, callable) );
                        }
                );

        return pairs;
    }

    private void callValidation(List<String> strings, Callable<String> callable) {
        try {
            strings.add( callable.call() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
