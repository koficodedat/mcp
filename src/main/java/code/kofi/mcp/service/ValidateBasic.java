package code.kofi.mcp.service;

import code.kofi.mcp.service.command.BasicCommand;

import java.util.ArrayList;
import java.util.List;

public class ValidateBasic {

    private List<BasicCommand> commands;

    public ValidateBasic(){ this.commands = new ArrayList<>(); }

    public void addCommand(BasicCommand command){
        this.commands.add( command );
    }

    public List<String> run(){
        List<String> values = new ArrayList<>();

        this.commands
                .forEach(
                        command -> {
                            String value = command.execute();
                            if( value != null ) values.add( value );
                        }
                );

        return values;
    }
}
