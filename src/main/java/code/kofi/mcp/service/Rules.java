package code.kofi.mcp.service;

import code.kofi.mcp.constant.CarMake;
import code.kofi.mcp.dto.Car;

import java.util.Arrays;

public class Rules {

    public static String verifyElectric( Car car ){

        if( car.getIsElectric().toUpperCase().matches("(\\bYES\\b|\\bTRUE\\b){1}") ){
            if( Arrays.stream(CarMake.values()).noneMatch( carMake -> carMake.getMake().equalsIgnoreCase( car.getMake() )  ) ) {
                return car.getMake() + " " + car.getModel() + " is not a known Electric Car";
            }
        }

        return null;
    }

    public static String verifyRange( Car car ){

        if( Integer.parseInt( car.getYear() ) < 2016 && Integer.parseInt( car.getRange() ) > 200 ){
            return car.getMake() + " " + car.getModel() + " cannot have a range of " + car.getRange() + " for model year " + car.getYear() ;
        }

        return null;
    }

}
