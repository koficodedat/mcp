package code.kofi.mcp.service;

import java.util.ArrayList;
import java.util.List;

public class ValidateBasic {

    private List<Command<String>> commands;

    public ValidateBasic(){ this.commands = new ArrayList<>(); }

    public void addCommand(Command<String> command){
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
