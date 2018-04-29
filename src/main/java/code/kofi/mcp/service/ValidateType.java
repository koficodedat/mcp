package code.kofi.mcp.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import code.kofi.mcp.constant.CarType;
import code.kofi.mcp.dto.Basic;
import code.kofi.mcp.util.Base;
import code.kofi.mcp.util.BasicValidationUtil;


@Service
public class ValidateType extends Validation{

	private static List<String> carTypes = Arrays
	.stream(
		CarType.values()
	)
	.map(
		( value ) -> { return value.getType(); }
	)
	.collect( Collectors.toList() );

	@Override
	public void  executeBasic( String column, Integer row, String value, Basic basic, Map<Integer,List<String>> map) {
		super.executeBasic(column, row, value, basic, map);
		if( !BasicValidationUtil.validateDescription( value, carTypes ) )
			Base.putValueInListMap(row, column + ": Invalid Description", map);
	}

	@Override
	public void  executeCustom( String column, Integer row, Map<Integer,List<String>> map ) {}

}