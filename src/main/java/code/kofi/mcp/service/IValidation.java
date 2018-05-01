package code.kofi.mcp.service;

import code.kofi.mcp.dto.Basic;

public interface IValidation {
    String executeBasic(String column, String value, Basic Basic );
    String executeCustom( String column );
}