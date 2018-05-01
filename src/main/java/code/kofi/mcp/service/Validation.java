package code.kofi.mcp.service;

import code.kofi.mcp.dto.Basic;
import code.kofi.mcp.util.BasicValidationUtil;
import org.springframework.stereotype.Service;

@Service
public abstract class Validation implements IValidation{

    @Override
    public String executeBasic(String column, String value, Basic basic ) {
        if( value == null )
            return column + ": is missing";
        else if( !BasicValidationUtil.validateDataType( value, basic.getType() ) )
           return column + ": has Invalid Datatype";
        else if( !BasicValidationUtil.validateFormat( value, basic.getFormat() ) )
            return column + ": has Invalid Format for value " + value;

        return null;
    }

    @Override
    public abstract String  executeCustom( String column );

}