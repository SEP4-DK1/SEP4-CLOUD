package tests.Controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import webapi.Controllers.BreadController;
import webapi.DAO.BreadDAO;
import webapi.Domain.BreadProfile;
import webapi.Domain.BreadTarget;
import webapi.Domain.Data;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class BreadControllerUnitTest
{
  @Mock
  private BreadDAO breadDAO;

  @InjectMocks
  private BreadController controller;

  @Test void getBread()
  {
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    BreadProfile bread1 = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);
    BreadProfile bread2 = new BreadProfile("Rundstykker", "runde stykker", dummyTargets);
    List<BreadProfile> breadList = new ArrayList<>();
    List<BreadProfile> returned;
    breadList.add(bread1); breadList.add(bread2);
    when(breadDAO.getAll()).thenReturn(breadList);
    returned = controller.getBread(null, null);
    assertArrayEquals(breadList.toArray(), returned.toArray());
  }

  @Test void newProfile()
  {
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    BreadProfile returned;
    BreadProfile profileToCreate = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);
    when(breadDAO.saveNewBread(any())).thenReturn(profileToCreate);
    returned = controller.newProfile(profileToCreate);
    assertEquals(profileToCreate, returned);
  }

  @Test void deleteBread()
  {
    ResponseEntity<?> returned;
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    BreadProfile profileToDelete = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);
    ResponseEntity<?> expected = new ResponseEntity<>(profileToDelete, HttpStatus.ACCEPTED);
    when(breadDAO.deleteBread(any(Long.class))).thenReturn(profileToDelete);
    returned = controller.deleteBread(1L);
    assertEquals(expected, returned);
  }

  @Test void updateBread()
  {
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    BreadProfile returned;
    BreadProfile profileToUpdate = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);
    when(breadDAO.updateBread(any())).thenReturn(profileToUpdate);
    returned = controller.updateBread(profileToUpdate);
    assertEquals(profileToUpdate, returned);
  }
}