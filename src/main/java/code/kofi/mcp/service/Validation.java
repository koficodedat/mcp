package code.kofi.mcp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import code.kofi.mcp.dto.Basic;
import code.kofi.mcp.util.Base;
import code.kofi.mcp.util.BasicValidationUtil;

@Service
public abstract class Validation implements IValidation{

    @Override
    public void executeBasic( String column, Integer row, String value, Basic basic, Map<Integer,List<String>> map ) {
        if( !BasicValidationUtil.validateDataType( value, basic.getType() ) )
            Base.putValueInListMap(row, column + ": Invalid Datatype", map);
        else if( !BasicValidationUtil.validateFormat( value, basic.getFormat() ) )
            Base.putValueInListMap(row, column + ": Invalid Format", map);
    }

    @Override
    public abstract void  executeCustom( String column, Integer row, Map<Integer,List<String>> map );

}