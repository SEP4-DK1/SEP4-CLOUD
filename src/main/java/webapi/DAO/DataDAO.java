package webapi.DAO;

import webapi.Data;

import java.util.List;

public interface DataDAO {

    public Data getLatest();
    public List<Data> getAll();
    public List<Data> getByTime(String fromDate, String fromTime, String toDate, String toTime);

    public void saveData(Data data);

}
