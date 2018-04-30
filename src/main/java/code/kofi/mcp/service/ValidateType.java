package code.kofi.mcp.service;

import code.kofi.mcp.constant.CarType;
import code.kofi.mcp.dto.Basic;
import code.kofi.mcp.util.Base;
import code.kofi.mcp.util.BasicValidationUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ValidateType extends Validation{

	private static final List<String> carTypes = Arrays
	.stream(
		CarType.values()
	)
	.map(
			CarType::getType
	)
	.collect( Collectors.toList() );

	@Override
	public void  executeBasic( String column, Integer row, String value, Basic basic, Map<Integer,List<String>> map) {
		super.executeBasic(column, row, value, basic, map);
		if( !BasicValidationUtil.validateDescription( value, carTypes ) )
			Base.putValueInListMap(row, column + ": has Invalid Description", map);
	}

	@Override
	public void  executeCustom( String column, Integer row, Map<Integer,List<String>> map ) {}

}