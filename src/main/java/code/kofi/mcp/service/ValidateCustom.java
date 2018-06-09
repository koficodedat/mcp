package code.kofi.mcp.service;

import code.kofi.mcp.service.command.CustomCommand;

import java.util.ArrayList;
import java.util.List;

public class ValidateCustom {

    private List< CustomCommand<List<String>, Validate> > commands;

    public ValidateCustom(){ this.commands = new ArrayList<>(); }

    public void addCommand(CustomCommand<List<String>, Validate> command){
        this.commands.add( command );
    }

    public List<String> run(){
        List<String> values = new ArrayList<>();

        this.commands
                .forEach(
                        command -> {
                            List<String> value = command.execute();
                            if( value != null ) values.addAll( value );
                        }
                );

        return values;
    }
}
