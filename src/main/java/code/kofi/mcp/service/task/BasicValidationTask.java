package code.kofi.mcp.service.task;

import code.kofi.mcp.conifg.ValidationFramework;
import code.kofi.mcp.dto.Car;
import code.kofi.mcp.service.*;
import code.kofi.mcp.service.command.BasicCommand;
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
public class BasicValidationTask extends RecursiveTask< Map<Integer,List<String>> > {

    private static final int THRESHOLD = 5;

    private Car[] cars;

    private ValidationFramework framework;

    public BasicValidationTask(ValidationFramework framework){
        this.framework = framework;
    }

    public BasicValidationTask setCars(Car[] cars) {
        this.cars = cars;
        return this;
    }

    @Override
    protected Map<Integer,List<String>> compute() {

        if( this.cars.length > THRESHOLD ){
            return ForkJoinTask
                    .invokeAll( createSubTask() )
                    .stream()
                    .map( task -> task.process( task.cars ) )
                    .collect( Collector.of( HashMap::new, HashMap::putAll, (left, right) -> { left.putAll(right); return left; } ) );
        }

        return this.process( this.cars );
    }

    private Collection<BasicValidationTask> createSubTask() {
        List<BasicValidationTask> dividedTask = new ArrayList<>();

        dividedTask.add( new BasicValidationTask( framework ).setCars( Arrays.copyOfRange( this.cars, 0, this.cars.length / 2 ) ) );
        dividedTask.add( new BasicValidationTask( framework ).setCars( Arrays.copyOfRange( this.cars, this.cars.length / 2, this.cars.length )  ) );

        return dividedTask;
    }

    private Map<Integer,List<String>> process(Car[] cars){

        List<Pair<Integer,List<String>>> pairs = new ArrayList<>();
        List<Pair<Integer, ValidateBasic>> root = new ArrayList<>();

        Arrays.stream( cars )
                .forEachOrdered(
                        car -> {
                            ValidateBasic validateBasic = new ValidateBasic();

                            this.framework.getCarValidation().getValidationCommands()
                                    .forEach(
                                            command -> {
                                                switch (command.getDescription()){
                                                    case "Type":
                                                        validateBasic.addCommand( new BasicCommand<>( command.getDescription(), car.getType(), command.getValidations(), new ValidateType() ) );
                                                        break;
                                                    case "Make":
                                                        validateBasic.addCommand( new BasicCommand<>( command.getDescription(), car.getMake(), command.getValidations(), new ValidateMake() ) );
                                                        break;
                                                    case "Model":
                                                        validateBasic.addCommand( new BasicCommand<>( command.getDescription(), car.getModel(), command.getValidations(), new ValidateModel() ) );
                                                        break;
                                                    case "Trim":
                                                        validateBasic.addCommand( new BasicCommand<>( command.getDescription(), car.getTrim(), command.getValidations(), new ValidateTrim() ) );
                                                        break;
                                                    case "Year":
                                                        validateBasic.addCommand( new BasicCommand<>( command.getDescription(), car.getYear(), command.getValidations(), new ValidateYear() ) );
                                                        break;
                                                    case "StreetSave":
                                                        validateBasic.addCommand( new BasicCommand<>( command.getDescription(), car.getIsStreetSave(), command.getValidations(), new ValidateStreetSave() ) );
                                                        break;
                                                    case "Electric":
                                                        validateBasic.addCommand( new BasicCommand<>( command.getDescription(), car.getIsElectric(), command.getValidations(), new ValidateElectric() ) );
                                                        break;
                                                    case "Range":
                                                        validateBasic.addCommand( new BasicCommand<>( command.getDescription(), car.getRange(), command.getValidations(), new ValidateRange() ) );
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            }
                                    );

                            root.add( new Pair<>( car.getRow(), validateBasic ) );
                        }
                );


        root.forEach( pair -> pairs.add( new Pair<>( pair.getKey(), pair.getValue().run() ) ) );

        return pairs
                .stream()
                .filter( pair -> pair.getValue().size() > 0 )
                .collect( Collectors.toMap(Pair::getKey, Pair::getValue) );

    }

}
