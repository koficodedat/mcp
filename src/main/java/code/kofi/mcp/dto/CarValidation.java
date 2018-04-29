package code.kofi.mcp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CarValidation {
    private Boolean canBeMultiThreaded;
    private Set<Command> commands;
}