package webapi.DAO;

import webapi.Domain.Data;
import webapi.Domain.SearchObject;

import java.util.List;

public interface DataDAO {
    public Data getLatest();

    public List<Data> getByTime(SearchObject searchObject);

    public Data saveNewData(Data data);
    public List<Data> getAll();
}
