package code.kofi.mcp.service;

import code.kofi.mcp.conifg.ValidationFramework;
import code.kofi.mcp.dto.Car;
import javafx.util.Pair;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collector;

@NoArgsConstructor
@Service
public class BasicValidationTask extends RecursiveTask< List<Pair<Integer,List<String>>> > {

    private static final int THRESHOLD = 10;

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

        dividedTask.add( new BasicValidationTask( framework ).setCars( Arrays.copyOfRange( this.cars, 0, this.cars.length / 2 ) ) );
        dividedTask.add( new BasicValidationTask( framework ).setCars( Arrays.copyOfRange( this.cars, this.cars.length / 2, this.cars.length )  ) );

        return dividedTask;
    }

    private List<Pair<Integer,List<String>>> process(Car[] cars){

        List<Pair<Integer,List<String>>> pairs = new ArrayList<>();
        List<ValidateBasic> root = new ArrayList<>();

        Arrays.stream( cars )
                .forEachOrdered(
                        car -> {
                            ValidateBasic validateBasic = new ValidateBasic();

                            this.framework.getCarValidation().getCommands()
                                    .forEach(
                                            command -> {
                                                switch (command.getDescription()){
                                                    case "Type":
                                                        validateBasic.addCommand( () -> new BasicCommand<>( command.getDescription(), car.getType(), command.getValidations().getBasic(), new ValidateType() ).execute() );
                                                        break;
                                                    case "Make":
                                                        validateBasic.addCommand( () -> new BasicCommand<>( command.getDescription(), car.getMake(), command.getValidations().getBasic(), new ValidateMake() ).execute() );
                                                        break;
                                                    case "Model":
                                                        validateBasic.addCommand( () -> new BasicCommand<>( command.getDescription(), car.getModel(), command.getValidations().getBasic(), new ValidateModel() ).execute() );
                                                        break;
                                                    case "Trim":
                                                        validateBasic.addCommand( () -> new BasicCommand<>( command.getDescription(), car.getTrim(), command.getValidations().getBasic(), new ValidateTrim() ).execute() );
                                                        break;
                                                    case "Year":
                                                        validateBasic.addCommand( () -> new BasicCommand<>( command.getDescription(), car.getYear(), command.getValidations().getBasic(), new ValidateYear() ).execute() );
                                                        break;
                                                    case "StreetSave":
                                                        validateBasic.addCommand( () -> new BasicCommand<>( command.getDescription(), car.getIsStreetSave(), command.getValidations().getBasic(), new ValidateStreetSave() ).execute() );
                                                        break;
                                                    case "Electric":
                                                        validateBasic.addCommand( () -> new BasicCommand<>( command.getDescription(), car.getIsElectric(), command.getValidations().getBasic(), new ValidateElectric() ).execute() );
                                                        break;
                                                    case "Range":
                                                        validateBasic.addCommand( () -> new BasicCommand<>( command.getDescription(), car.getRange(), command.getValidations().getBasic(), new ValidateRange() ).execute() );
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            }
                                    );

                            root.add(validateBasic);
                        }
                );


        root.forEach( validateBasic -> pairs.add( new Pair<>( pairs.size() + 1, validateBasic.run() ) ) );

        return pairs;
    }

}
