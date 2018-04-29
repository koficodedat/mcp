package code.kofi.mcp.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;

import org.springframework.web.multipart.MultipartFile;

import code.kofi.mcp.dto.Car;

public class Base {

    public static List<Car> buildCarListFromFile(MultipartFile file){

        if( !file.isEmpty() ){
            try{

                byte[] fileInBytes = file.getBytes();
                InputStream inputStream =  new ByteArrayInputStream(fileInBytes);
                return Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, Car.class);

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        return null;
    }

    public static void putValueInListMap(Integer key, String value, Map<Integer,List<String>> map){
        if( map.containsKey(key) ) map.get(key).add(value);
        else map.put(key, Arrays.asList(value));
    }
}