package code.kofi.mcp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

import lombok.EqualsAndHashCode;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Validations {
    private Basic basic;
    private Set<String> custom;
}