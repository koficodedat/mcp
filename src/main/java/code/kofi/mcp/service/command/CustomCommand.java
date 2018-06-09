package code.kofi.mcp.service.command;

import code.kofi.mcp.dto.Car;
import code.kofi.mcp.dto.Validations;
import code.kofi.mcp.service.Validate;

import java.util.List;

public class CustomCommand<C,T extends Validate> implements Command<List<String>> {

    private Car car;
    private Validations validations;
    private T validate;

    public CustomCommand(Car car, Validations validations, T validate){
        this.car = car;
        this.validations = validations;
        this.validate = validate;
    }

    @Override
    public List<String> execute() { return this.validate.executeCustom( this.car, this.validations ); }

}
