package code.kofi.mcp.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;

import org.springframework.web.multipart.MultipartFile;

import code.kofi.mcp.dto.Car;

public class Base {

    public static Car[] buildCarListFromFile(MultipartFile file){

        if( !file.isEmpty() ){
            try{

                byte[] fileInBytes = file.getBytes();
                InputStream inputStream =  new ByteArrayInputStream(fileInBytes);

                List<Car> cars = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, Car.class);

                return cars.stream().toArray(Car[]::new);

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        return null;
    }
}