package code.kofi.mcp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ValidateMake extends Validation{

	@Override
	public void executeCustom( String column, Integer row, Map<Integer,List<String>> map ) {}

}