package code.kofi.mcp.constant;

import lombok.Getter;

@Getter
public enum CarType {
    SEDAN("SEDAN"),
    COUP("COUP"),
    TRUCK("TRUCK"),
    BUS("BUS")
    ;

    private final String type;

    CarType(String type){
        this.type = type;
    }
}