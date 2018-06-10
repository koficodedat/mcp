package code.kofi.mcp.service.task;

import code.kofi.mcp.conifg.ValidationFramework;
import code.kofi.mcp.dto.Car;
import code.kofi.mcp.service.*;
import code.kofi.mcp.service.command.CustomCommand;
import javafx.util.Pair;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class CustomValidationTask extends RecursiveTask< Map<Integer,List<String>> > {

    private static final int THRESHOLD = 100;

    private Car[] cars;

    private ValidationFramework framework;

    public CustomValidationTask(ValidationFramework framework){
        this.framework = framework;
    }

    public CustomValidationTask setCars(Car[] cars) {
        this.cars = cars;
        return this;
    }

    @Override
    protected Map<Integer, List<String>> compute() {

        if( this.cars.length > THRESHOLD ){
            return ForkJoinTask
                    .invokeAll( createSubTask() )
                    .stream()
                    .map( task -> task.process( task.cars ) )
                    .collect( Collector.of( HashMap::new, HashMap::putAll, (left, right) -> { left.putAll(right); return left; } ) );
        }

        return this.process( this.cars );
    }

    private Collection<CustomValidationTask> createSubTask() {
        List<CustomValidationTask> dividedTask = new ArrayList<>();

        dividedTask.add( new CustomValidationTask( framework ).setCars( Arrays.copyOfRange( this.cars, 0, this.cars.length / 2 ) ) );
        dividedTask.add( new CustomValidationTask( framework ).setCars( Arrays.copyOfRange( this.cars, this.cars.length / 2, this.cars.length )  ) );

        return dividedTask;
    }

    private Map<Integer,List<String>> process(Car[] cars){

        List<Pair<Integer,List<String>>> pairs = new ArrayList<>();
        List<Pair<Integer, ValidateCustom>> root = new ArrayList<>();

        Arrays.stream( cars )
                .forEachOrdered(
                        car -> {
                            ValidateCustom validateCustom = new ValidateCustom();

                            this.framework.getCarValidation().getValidationCommands()
                                    .forEach(
                                            command -> {
                                                switch (command.getDescription()){
                                                    case "Type":
                                                        validateCustom.addCommand( new CustomCommand<>( car, command.getValidations(), new ValidateType() ) );
                                                        break;
                                                    case "Make":
                                                        validateCustom.addCommand( new CustomCommand<>( car, command.getValidations(), new ValidateMake() ) );
                                                        break;
                                                    case "Model":
                                                        validateCustom.addCommand( new CustomCommand<>( car, command.getValidations(), new ValidateModel() ) );
                                                        break;
                                                    case "Trim":
                                                        validateCustom.addCommand(  new CustomCommand<>( car, command.getValidations(), new ValidateTrim() ) );
                                                        break;
                                                    case "Year":
                                                        validateCustom.addCommand( new CustomCommand<>( car, command.getValidations(), new ValidateYear() ) );
                                                        break;
                                                    case "StreetSave":
                                                        validateCustom.addCommand( new CustomCommand<>( car, command.getValidations(), new ValidateStreetSave() ) );
                                                        break;
                                                    case "Electric":
                                                        validateCustom.addCommand( new CustomCommand<>( car, command.getValidations(), new ValidateElectric() ) );
                                                        break;
                                                    case "Range":
                                                        validateCustom.addCommand( new CustomCommand<>( car, command.getValidations(), new ValidateRange() ) );
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            }
                                    );

                            root.add( new Pair<>( car.getRow(), validateCustom ) );;
                        }
                );

        root.forEach( pair -> pairs.add( new Pair<>( pair.getKey(), pair.getValue().run() ) ) );

        return pairs
                .stream()
                .filter( pair -> pair.getValue().size() > 0 )
                .collect( Collectors.toMap(Pair::getKey, Pair::getValue) );
    }
}
