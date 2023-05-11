package webapi;

import org.springframework.web.bind.annotation.*;
import webapi.DAO.DataDAO;

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
    List<Data> byTimeOrLatest(@RequestBody String fromDate ,@RequestBody String fromTime,@RequestBody String toDate, @RequestBody String toTime){
        if(fromDate.isEmpty() && !fromDate.isBlank() && !fromTime.isEmpty() && !fromTime.isBlank() && !toDate.isEmpty() && !toDate.isBlank() && !toTime.isEmpty() && !toTime.isBlank()){
            return dataDAO.getByTime(fromDate, fromTime, toDate, toTime);
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
}
