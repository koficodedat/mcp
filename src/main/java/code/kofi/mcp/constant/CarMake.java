package code.kofi.mcp.constant;

import lombok.Getter;

@Getter
public enum CarMake {

    TESLA("TESLA")
    ;

    private final String make;

    CarMake(String make){
        this.make = make;
    }

}
