package code.kofi.mcp.conifg;

import org.springframework.stereotype.Component;

import code.kofi.mcp.dto.CarValidation;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Component
@ConfigurationProperties("framework")
public class ValidationFramework {
    private CarValidation carValidation;
}