package code.kofi.mcp.factory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import code.kofi.mcp.dto.Car;

public class TestBench {

    public static Car getCar(
        String type, 
        String make, 
        String model, 
        String trim, 
        String year,
        String range, 
        String isStreetSave, 
        String isElectric
    ){
        return new Car()
            .setType(type)
            .setMake(make)
            .setModel(model)
            .setTrim(trim)
            .setYear(year)
            .setRange(range)
            .setIsStreetSave(isStreetSave)
            .setIsElectric(isElectric);
    }

    public static Car[] getCars(){

        return new Car[]{
            getCar("coup", "bmw", "m3", "mid", "2015", "230", "true", "false"),
            getCar("sedan", "audi", "a3", "mid", "2017", "350", "yes", "no"),
            getCar("sedan", "tesla", "model 3", "lux", "2018", "250", "yes", "yes"),
            getCar("bus", "deawoo", "BH120F Royal Cruiser II", "mid", "2009", "300", "-", "no"),
            getCar("coup", "bugati", "veron", "super lux", "2016", "150", "no", "no"),
            getCar("truck", "benz", "550", "lux", "20174", "200", "", "no"),
            getCar("ewd", "range rover", "sport", "lux", "2018", "250", "yes", ""),
            getCar("gibberish", "bmw", "m4", "basic", "2015", "230", "yes", "no")
        };

    }
}