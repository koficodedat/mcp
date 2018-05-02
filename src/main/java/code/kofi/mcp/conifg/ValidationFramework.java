package code.kofi.mcp.conifg;

import code.kofi.mcp.dto.CarValidation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Component
@ConfigurationProperties("framework")
public class ValidationFramework {
    private CarValidation carValidation;
}