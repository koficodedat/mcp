package code.kofi.mcp.service;

import code.kofi.mcp.dto.Car;
import code.kofi.mcp.dto.Validations;

import java.util.List;

public interface IValidate {
    String executeBasic( String column, String value, Validations validations );
    List<String> executeCustom(Car car, Validations validations );
}