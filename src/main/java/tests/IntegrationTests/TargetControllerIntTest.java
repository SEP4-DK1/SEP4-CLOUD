package tests.IntegrationTests;

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
import webapi.Controllers.TargetController;
import webapi.DAO.TargetDAO;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TargetControllerIntTest {
  @Autowired
  private MockMvc mockMvc;

  @Mock
  private TargetDAO targetDAO;

  @InjectMocks
  private TargetController targetController;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(targetController).build();
  }

  @Test
  public void testGetTarget() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/target")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());}

  @Test
  public void testNewTarget() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/target")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"temp\": \"25\", \"humidity\": \"50\", \"co2\": \"30\", \"timeToActivate\": \"16-05-2023 10:00:00\"}]"))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }
}