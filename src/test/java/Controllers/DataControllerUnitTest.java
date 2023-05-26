package Controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webapi.Controllers.DataController;
import webapi.DAO.DataDAO;
import webapi.Domain.Data;
import webapi.Domain.SearchObject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataControllerUnitTest
{
  @Mock
  private DataDAO dataDAO;

  @InjectMocks
  private DataController controller;

  @Test void byTime()
  {
    List<Data> returnedData;
    List<Data> dataToReturn = new ArrayList<>();
    Data data1 = new Data("30", "30", "30", "19-05-2023 10:36:00");
    Data data2 = new Data("35", "35", "35", "19-05-2023 10:37:00");
    dataToReturn.add(data1); dataToReturn.add(data2);
    String fromDate = "19-05-2023 10:00:00";
    String toDate = "20-05-2023 15:00:00";
    SearchObject searchObject = new SearchObject(fromDate, toDate);
    when(dataDAO.getByTime(any(SearchObject.class))).thenReturn(dataToReturn);
    returnedData = dataDAO.getByTime(searchObject);
    assertArrayEquals(dataToReturn.toArray(), returnedData.toArray());
  }

  @Test void latest()
  {
    Data returnedData;
    Data dataToReturn = new Data("30", "30", "30", "19-05-2023 10:36:00");
    when(dataDAO.getLatest()).thenReturn(dataToReturn);
    returnedData = dataDAO.getLatest();
    assertEquals(dataToReturn, returnedData);
  }



  @Test void all()
  {
    List<Data> returnedData;
    List<Data> mockData = new ArrayList<>();
    Data data1 = new Data("25", "25", "25", "19-05-2023 10:35:00");
    Data data2 = new Data("30", "30", "30", "19-05-2023 10:36:00");
    Data data3 = new Data("35", "35", "35", "19-05-2023 10:37:00");
    mockData.add(data1); mockData.add(data2); mockData.add(data3);
    when(dataDAO.getAll()).thenReturn(mockData);
    returnedData = controller.all();
    assertArrayEquals(mockData.toArray(), returnedData.toArray());
  }

  @Test void newData()
  {
    Data dataToCreate = new Data("25", "25", "25", "19-05-2023 10:35:00");
    Data dataReturned;
    when(dataDAO.saveNewData(any())).thenReturn(dataToCreate);
    dataReturned = controller.newData(dataToCreate);
    assertEquals(dataToCreate, dataReturned);
  }
}