package tests.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import webapi.Controllers.DataController;
import webapi.DAO.DataDAO;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DataControllerIntTest
{
  @Autowired private MockMvc mockMvc;

  @Mock private DataDAO dataDAO;

  @InjectMocks private DataController dataController;

  @BeforeEach public void setup()
  {
    mockMvc = MockMvcBuilders.standaloneSetup(dataController).build();
  }

  @Test public void testByTimeOrLatest_withSearchObject() throws Exception
  {
    mockMvc.perform(MockMvcRequestBuilders.get(
                "/data?fromDate=01-05-2023 09:00:30&toDate=17-05-2023 19:00:20")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test public void testByTimeOrLatest_withoutSearchObject() throws Exception
  {
    mockMvc.perform(MockMvcRequestBuilders.get("/data")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test public void testAll() throws Exception
  {

    mockMvc.perform(MockMvcRequestBuilders.get("/data/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }

  @Test public void testNewData() throws Exception
  {
    mockMvc.perform(MockMvcRequestBuilders.post("/data")
            .contentType(MediaType.APPLICATION_JSON).content(
                "{\"temp\": \"25\", \"humidity\": \"50\", \"co2\": \"30\", \"timestamp\": \"16-05-2023 10:00:00\"}"))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }
}
