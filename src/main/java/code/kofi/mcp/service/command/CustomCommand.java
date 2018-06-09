package code.kofi.mcp.service.command;

import code.kofi.mcp.dto.Validations;
import code.kofi.mcp.service.Validate;

public class CustomCommand<T extends Validate> implements Command {

    private String column;
    private String value;
    private Validations validations;
    private T validate;

    public CustomCommand(String column, String value, Validations validations, T validate){
        this.column = column;
        this.value = value;
        this.validations = validations;
        this.validate = validate;
    }

    @Override
    public String execute() { return this.validate.executeCustom( this.column, this.value, this.validations ); }

}
