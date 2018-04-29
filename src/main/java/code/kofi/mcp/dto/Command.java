package code.kofi.mcp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Command {
    private String description;
    private Boolean inParallel;
    private Validations validations;

}