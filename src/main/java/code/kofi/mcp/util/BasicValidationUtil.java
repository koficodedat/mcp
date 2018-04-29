package code.kofi.mcp.util;

import java.util.List;

public class BasicValidationUtil {

    public static Boolean validateDescription(String description, List<String> carTypes){
        return carTypes.contains( description.toUpperCase() );
    }

    public static Boolean validateDataType(String data, String type){

        switch( type ){

            case "String":
                return data instanceof String;
            case "Integer":
                try{ 
                    return Integer.valueOf( data ) instanceof Integer;
                } catch(NumberFormatException exception){
                    exception.printStackTrace();
                }
            case "Boolean":
                try{ 
                    return Boolean.valueOf( data ) instanceof Boolean;
                } catch(NumberFormatException exception){
                    exception.printStackTrace();
                }
            default:
                break;
        }
        
        return false;
    }

    public static Boolean validateFormat(String data, String format){
        return !format.isEmpty() ? data.matches(format) : true;
    }
    
}