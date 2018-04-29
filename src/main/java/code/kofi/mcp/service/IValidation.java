package code.kofi.mcp.service;

import java.util.List;
import java.util.Map;

import code.kofi.mcp.dto.Basic;

public interface IValidation {
    void executeBasic( String column, Integer row, String value, Basic Basic, Map<Integer,List<String>> map );
    void executeCustom( String column, Integer row, Map<Integer, List<String>> map );
}