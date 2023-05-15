package webapi;

import org.springframework.web.bind.annotation.*;
import webapi.Database.DataDAO;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {

    private final DataDAO dataDAO;

    DataController(DataDAO dataDAO){
        this.dataDAO = dataDAO;
    }
    @CrossOrigin
    @GetMapping("/data")
    List<Data> byTimeOrLatest(@RequestBody (required = false)SearchObject searchObject){
        if(searchObject != null && searchObject.isNotEmptyOrBlank()){
            return dataDAO.getByTime(searchObject);
        }
        List<Data> dataList = new ArrayList<>();
        dataList.add(dataDAO.getLatest());
        return dataList;
    }

    @CrossOrigin
    @GetMapping("/data/all")
    List<Data> all(){
        return dataDAO.getAll();
    }

    @CrossOrigin
    @PostMapping("/data")
    void newData(@RequestBody Data newData){
        dataDAO.saveNewData(newData);
    }
}
