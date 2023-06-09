package webapi.DAO;

import com.google.api.client.util.DateTime;
import jnr.constants.platform.Local;
import org.springframework.stereotype.Service;
import webapi.Domain.Data;
import webapi.Domain.SearchObject;
import webapi.Repositories.DataRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class DataDAOImpl implements DataDAO{
    private final DataRepository dataRepository;

    public DataDAOImpl(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    @Override public Data getLatest()
    {
        List<Data> allData = dataRepository.findAll();
        if(allData.size()>0){
            Data latest = allData.get(0);
            for(Data d:allData){
                if(d.getId()>latest.getId()){
                    latest = d;
                }
            }
            return latest;
        }
        return null;
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
        TimeZone timeZone = TimeZone.getTimeZone("GMT+2");
        LocalDateTime currentTime = LocalDateTime.now(timeZone.toZoneId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy k:mm:ss");
        data.setTimestamp(currentTime.format(formatter));
        return this.dataRepository.save(data);
    }

    @Override public List<Data> getAll()
    {
        return this.dataRepository.findAll();
    }
}
