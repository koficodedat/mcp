package code.kofi.mcp.util;

import java.util.List;

public class BasicValidationUtil {

    public static Boolean validateDescription(String description, List<String> carTypes){
        return carTypes.contains( description.toUpperCase() );
    }

    public static Boolean validateDataType(String data, String type){

        if( type.equals("Integer") ){
            try{
                return Integer.valueOf(data) instanceof Integer;
            }catch (NumberFormatException ex){
                ex.printStackTrace();
                return false;
            }
        }
        else return true;

    }

    public static Boolean validateFormat(String data, String format){
        return data.matches(format);
    }
    
}