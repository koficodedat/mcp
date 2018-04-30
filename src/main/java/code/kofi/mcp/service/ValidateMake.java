package code.kofi.mcp.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ValidateMake extends Validation{

	@Override
	public void executeCustom( String column, Integer row, Map<Integer,List<String>> map ) {}

}