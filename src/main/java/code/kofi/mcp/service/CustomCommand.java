package code.kofi.mcp.service;

public class CustomCommand<T extends Validation> implements Command {

    private T validation;
    private String column;

    CustomCommand(String column, T validation){
        this.column = column;
        this.validation = validation;
    }

    @Override
    public String execute() {
        return this.validation.executeCustom( this.column );
    }

}
