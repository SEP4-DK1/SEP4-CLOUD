package DAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webapi.DAO.DataDAOImpl;
import webapi.Domain.Data;
import webapi.Domain.SearchObject;
import webapi.Repositories.DataRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataDAOImplUnitTest
{

  @Mock
  DataRepository repository;

  @InjectMocks
  private DataDAOImpl dataDAO;

  @Test void getLatest()
  {
    List<Data> dataList = new ArrayList<Data>();
    Data latest;
    Data data1 = new Data("25", "25", "25", "19-05-2023 10:35:00");
    Data data2 = new Data("30", "30", "30", "19-05-2023 10:36:00");
    Data data3 = new Data("35", "35", "35", "19-05-2023 10:37:00");
    Data data4 = new Data("35", "35", "35", "20-05-2023 10:38:00");
    Data data5 = new Data("35", "35", "35", "20-05-2023 15:40:00");
    data1.setId(1); data2.setId(2); data3.setId(3); data4.setId(4); data5.setId(5);
    dataList.add(data1); dataList.add(data2); dataList.add(data3); dataList.add(data4); dataList.add(data5);
    when(repository.findAll()).thenReturn(dataList);
    latest = dataDAO.getLatest();
    assertEquals(data5, latest);
  }

  @Test void getLatestNoneReturned()
  {
    List<Data> datalist = new ArrayList<>();
    when(repository.findAll()).thenReturn(datalist);
    Data latest = dataDAO.getLatest();
    assertNull(latest);
  }

  @Test void getByTime()
  {
    List<Data> returned;
    List<Data> expectedList = new ArrayList<>();
    List<Data> dataList = new ArrayList<Data>();
    SearchObject searchObject = new SearchObject("19-05-2023 10:36:30", "20-05-2023 11:00:00");
    Data data1 = new Data("25", "25", "25", "19-05-2023 10:35:00");
    Data data2 = new Data("30", "30", "30", "19-05-2023 10:36:00");
    Data data3 = new Data("35", "35", "35", "19-05-2023 10:37:00");
    Data data4 = new Data("35", "35", "35", "20-05-2023 10:38:00");
    Data data5 = new Data("35", "35", "35", "20-05-2023 15:40:00");
    data1.setId(1); data2.setId(2); data3.setId(3); data4.setId(4); data5.setId(5);
    dataList.add(data1); dataList.add(data2); dataList.add(data3); dataList.add(data4); dataList.add(data5);
    expectedList.add(data3); expectedList.add(data4);
    when(repository.findAll()).thenReturn(dataList);
    returned = dataDAO.getByTime(searchObject);
    assertEquals(expectedList, returned);
  }

  @Test void saveNewData()
  {
    Data data = new Data("25", "25", "25", "19-05-2023 10:35:00");
    Data returned;
    when(repository.save(any())).thenReturn(data);
    returned = dataDAO.saveNewData(data);
    assertEquals(data, returned);
  }

  @Test void getAll()
  {
    List<Data> dataList = new ArrayList<>();
    List<Data> returned = new ArrayList<>();
    Data data1 = new Data("25", "25", "25", "19-05-2023 10:35:00");
    Data data2 = new Data("30", "30", "30", "19-05-2023 10:36:00");
    Data data3 = new Data("35", "35", "35", "19-05-2023 10:37:00");
    Data data4 = new Data("35", "35", "35", "20-05-2023 10:38:00");
    Data data5 = new Data("35", "35", "35", "20-05-2023 15:40:00");
    dataList.add(data1); dataList.add(data2); dataList.add(data3); dataList.add(data4); dataList.add(data5);
    when(repository.findAll()).thenReturn(dataList);
    returned = dataDAO.getAll();
    assertArrayEquals(dataList.toArray(), returned.toArray());
  }
}