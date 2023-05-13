package webapi.Database;

import org.springframework.stereotype.Service;
import webapi.Data;
import webapi.SearchObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataDAOImpl implements DataDAO{
    private final DataRepository dataRepository;

    public DataDAOImpl(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    @Override public Data getLatest()
    {
        return this.dataRepository.getMaxId();
    }

    @Override public List<Data> getByTime(SearchObject searchObject)
    {
        List<Data> toReturn = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy k:mm:ss");
        LocalDateTime dateFrom = LocalDateTime.parse(searchObject.getFromDate(), formatter);
        LocalDateTime dateTo = LocalDateTime.parse(searchObject.getToDate(), formatter);
        List<Data> data = dataRepository.findAll();
        for(Data d : data){
            LocalDateTime dateFormatted = LocalDateTime.parse(d.getTimestamp(), formatter);
            if(dateFormatted.isAfter(dateFrom) && dateFormatted.isBefore(dateTo)){
                toReturn.add(d);
            }
        }
        return toReturn;
    }

    public Data saveNewData(Data data){
        data.setTimestamp(LocalDateTime.now().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy k:mm:ss");
        data.setTimestamp(LocalDateTime.now().format(formatter));
        return this.dataRepository.save(data);
    }

    @Override public List<Data> getAll()
    {
        return this.dataRepository.findAll();
    }
}
