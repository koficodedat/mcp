package code.kofi.mcp.service;

import code.kofi.mcp.constant.CarType;
import code.kofi.mcp.dto.Basic;
import code.kofi.mcp.dto.Validations;
import code.kofi.mcp.util.BasicValidationUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ValidateType extends Validate {

	private static final List<String> carTypes = Arrays
	.stream(
		CarType.values()
	)
	.map(
			CarType::getType
	)
	.collect( Collectors.toList() );

	@Override
	public String executeBasic( String column, String value, Validations validations ) {

		String superString = super.executeBasic(column, value, validations);
		if( superString == null && !BasicValidationUtil.validateDescription( value, carTypes ) ) return column + ": has Invalid Description";
		return superString;
	}

}