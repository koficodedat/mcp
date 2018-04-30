package code.kofi.mcp.service;

import code.kofi.mcp.dto.Basic;
import code.kofi.mcp.util.Base;
import code.kofi.mcp.util.BasicValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public abstract class Validation implements IValidation{

    @Override
    public void executeBasic( String column, Integer row, String value, Basic basic, Map<Integer,List<String>> map ) {
        if( value == null )
            Base.putValueInListMap(row, column + ": is missing", map);
        else if( !BasicValidationUtil.validateDataType( value, basic.getType() ) )
            Base.putValueInListMap(row, column + ": has Invalid Datatype", map);
        else if( !BasicValidationUtil.validateFormat( value, basic.getFormat() ) )
            Base.putValueInListMap(row, column + ": has Invalid Format for value " + value, map);
    }

    @Override
    public abstract void  executeCustom( String column, Integer row, Map<Integer,List<String>> map );

}