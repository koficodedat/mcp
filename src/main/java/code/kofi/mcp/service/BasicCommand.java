package code.kofi.mcp.service;

import code.kofi.mcp.dto.Basic;

public class BasicCommand<T extends Validation> implements Command {

    private T validation;
    private String column;
    private String value;
    private Basic basic;

    BasicCommand(String column, String value, Basic basic, T validation){
        this.column = column;
        this.value = value;
        this.basic = basic;
        this.validation = validation;
    }

    @Override
    public String execute() {
        return this.validation.executeBasic( this.column, this.value, this.basic );
    }

}
