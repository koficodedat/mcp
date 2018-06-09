package code.kofi.mcp.service;

import code.kofi.mcp.constant.CustomRules;
import code.kofi.mcp.dto.Car;
import code.kofi.mcp.dto.Validations;
import code.kofi.mcp.util.BasicValidationUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class Validate implements IValidate {

    @Override
    public String executeBasic( String column, String value, Validations validations ) {
        if( value == null )
            return column + ": is missing";
        else if( !BasicValidationUtil.validateDataType( value, validations.getBasic().getType() ) )
           return column + ": has Invalid Data Type";
        else if( !BasicValidationUtil.validateFormat( value, validations.getBasic().getFormat() ) )
            return column + ": has Invalid Format for value " + value;

        return null;
    }

    @Override
    public List<String> executeCustom(Car car, Validations validations ) {

        String[] customValidations = validations.getCustom();

        return Arrays.stream(customValidations)
                .parallel()
                .filter( rule -> !rule.equals("") )
                .map( rule -> CustomRules.valueOf( rule ).getFunction().apply(car) )
                .filter( Objects::nonNull )
                .collect(Collectors.toList());
    }

}