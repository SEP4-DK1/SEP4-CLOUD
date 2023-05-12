package webapi.Database;

import webapi.Data;
import webapi.SearchObject;

import java.util.List;

public interface DataDAO {
    public Data getLatest();

    public List<Data> getByTime(SearchObject searchObject);

    public Data saveNewData(Data data);
    public List<Data> getAll();
}
