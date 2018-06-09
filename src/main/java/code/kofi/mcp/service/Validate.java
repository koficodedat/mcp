package code.kofi.mcp.service;

import code.kofi.mcp.dto.Basic;
import code.kofi.mcp.dto.ValidationCommand;
import code.kofi.mcp.dto.Validations;
import code.kofi.mcp.util.BasicValidationUtil;
import org.springframework.stereotype.Service;

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
    public String executeCustom( String column, String value, Validations validations ) { return null; };

}