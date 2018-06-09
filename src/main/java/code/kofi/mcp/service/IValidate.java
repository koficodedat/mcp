package code.kofi.mcp.service;

import code.kofi.mcp.dto.Validations;

public interface IValidate {
    String executeBasic( String column, String value, Validations validations );
    String executeCustom( String column, String value, Validations validations );
}