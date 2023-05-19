package webapi.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import webapi.Domain.Data;
import webapi.DAO.DataDAO;
import webapi.Domain.SearchObject;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {

    private final DataDAO dataDAO;

    public DataController(DataDAO dataDAO){
        this.dataDAO = dataDAO;
    }
    @CrossOrigin
    @GetMapping("/data")
    public List<Data> byTimeOrLatest(@RequestParam (required = false)String fromDate, @RequestParam (required=false)String toDate){
        SearchObject searchObject = new SearchObject();
        searchObject.setFromDate(fromDate);
        searchObject.setToDate(toDate);
        if(searchObject.isNotEmptyOrBlank()){
            return dataDAO.getByTime(searchObject);
        }
        List<Data> dataList = new ArrayList<>();
        dataList.add(dataDAO.getLatest());
        return dataList;
    }

    @CrossOrigin
    @GetMapping("/data/all")
    public List<Data> all(){
        return dataDAO.getAll();
    }

    @CrossOrigin
    @PostMapping("/data")
    @ResponseStatus(HttpStatus.CREATED)
    public Data newData(@RequestBody Data newData){
        return dataDAO.saveNewData(newData);
    }
}
