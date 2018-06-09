package code.kofi.mcp.service;

import code.kofi.mcp.dto.Validations;
import org.springframework.stereotype.Service;

@Service
public class ValidateElectric extends Validate {
    @Override
    public String executeCustom( String column, String value, Validations validations ) {
        return "";
    }
}