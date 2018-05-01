package code.kofi.mcp.dto;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@EqualsAndHashCode
@Component
public class Car {
    @ExcelRow
    private int row;
    @ExcelCell(0)
    private String type;
    @ExcelCell(1)
    private String make;
    @ExcelCell(2)
    private String model;
    @ExcelCell(3)
    private String trim;
    @ExcelCell(4)
    private String year;
    @ExcelCell(5)
    private String range;
    @ExcelCell(6)
    private String isStreetSave;
    @ExcelCell(7)
    private String isElectric;
    
    /**
     * @param row the row to set
     */
    public Car setRow(Integer row) {
        this.row = row;
        return this;
    }

    /**
     * @param type the type to set
     */
    public Car setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @param make the make to set
     */
    public Car setMake(String make) {
        this.make = make;
        return this;
    }

    /**
     * @param model the model to set
     */
    public Car setModel(String model) {
        this.model = model;
        return this;
    }

    /**
     * @param trim the trim to set
     */
    public Car setTrim(String trim) {
        this.trim = trim;
        return this;
    }

    /**
     * @param year2 the year to set
     */
    public Car setYear(String year2) {
        this.year = year2;
        return this;
    }

    /**
     * @param range the range to set
     */
    public Car setRange(String range) {
        this.range = range;
        return this;
    }

    /**
     * @param isStreetSave the isStreetSave to set
     */
    public Car setIsStreetSave(String isStreetSave) {
        this.isStreetSave = isStreetSave;
        return this;
    }

    /**
     * @param isElectric the isElectric to set
     */
    public Car setIsElectric(String isElectric) {
        this.isElectric = isElectric;
        return this;
    }
}