package IntegrationTests;

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
import webapi.Controllers.BreadController;
import webapi.DAO.BreadDAO;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BreadControllerIntTest {
  @Autowired
  private MockMvc mockMvc;

  @Mock
  private BreadDAO breadDAO;

  @InjectMocks
  private BreadController breadController;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(breadController).build();
  }

  @Test
  public void testGetBread() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bread")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());}

  @Test
  public void testGetBread_withoutId() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bread?id=1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());}
  @Test
  public void testGetBread_withoutTitle() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bread?title=bread")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());}
  @Test
  public void testGetBread_withTitleAndId() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bread?id=1&title=bread")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());}
  @Test
  public void testNewBread() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/bread")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Brød\", \"Description\": \"Bare brød\", \"targets\": [{\"temp\": \"25\", \"humidity\": \"50\", \"co2\": \"30\", \"offsetTime\": \"16-05-2023 10:00:00\"}]}"))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }
  @Test
  public void testDeleteBread() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/bread?id=1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testUpdateBread() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/bread")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Brød\", \"Description\": \"Bare brød\", \"targets\": [{\"temp\": \"25\", \"humidity\": \"50\", \"co2\": \"30\", \"offsetTime\": \"16-05-2023 10:00:00\"}]}"))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }
}
