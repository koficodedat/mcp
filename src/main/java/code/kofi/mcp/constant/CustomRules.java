package code.kofi.mcp.constant;

import code.kofi.mcp.dto.Car;
import code.kofi.mcp.service.Rules;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum CustomRules {

    VERIFY_ELECTRIC( "VERIFY_ELECTRIC", Rules::verifyElectric ),
    VERIFY_RANGE( "VERIFY_RANGE", Rules::verifyRange )
    ;

    private final String description;
    private final Function<Car,String> function;

    CustomRules(String description, Function<Car,String> function){
        this.description = description;
        this.function = function;
    }

}
