package code.kofi.mcp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import code.kofi.mcp.dto.Basic;
import code.kofi.mcp.util.Base;

@Service
public class ValidateElectric extends Validation{

	@Override
	public void executeBasic( String column, Integer row, String value, Basic basic, Map<Integer,List<String>> map) {
		super.executeBasic(column, row, value, basic, map);
		if( map.get( row ).size() == 0 ){
			String value_ = value.trim();
			if( 
				!value_.equalsIgnoreCase("yes") &&
				!value_.equalsIgnoreCase("no") &&
				!value_.equalsIgnoreCase("true") &&
				!value_.equalsIgnoreCase("false")
				)
				Base.putValueInListMap(row, column + ": Invalid Format", map);
		}
	}

	@Override
	public void executeCustom( String column, Integer row, Map<Integer,List<String>> map ) {}

}