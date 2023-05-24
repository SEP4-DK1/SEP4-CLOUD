package tests.DAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webapi.DAO.BreadDAOImpl;
import webapi.DAO.DataDAOImpl;
import webapi.Domain.BreadProfile;
import webapi.Domain.BreadTarget;
import webapi.Repositories.BreadRepository;
import webapi.Repositories.DataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BreadDAOImplUnitTest
{

  @Mock BreadRepository repository;

  @InjectMocks
  private BreadDAOImpl breadDAO;

  @Test void getById()
  {
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    BreadProfile bread1 = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);
    Optional<BreadProfile> expected = Optional.of(bread1);
    when(repository.findById(any())).thenReturn(expected);
    Optional<BreadProfile> returned = breadDAO.getById(1L);
    assertEquals(expected, returned);
  }

  @Test void getByTitle()
  {
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    List<BreadProfile> breadList = new ArrayList<>();
    BreadProfile bread1 = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);
    BreadProfile bread2 = new BreadProfile("Burgerboller", "Burger boller", dummyTargets);
    BreadProfile bread3 = new BreadProfile("Fastelavnsboller", "fastelavns boller", dummyTargets);
    BreadProfile bread4 = new BreadProfile("Fødselsdagsboller", "fødselsdags boller", dummyTargets);
    BreadProfile bread5 = new BreadProfile("Rundstykker", "runde stykker", dummyTargets);
    breadList.add(bread1); breadList.add(bread2);breadList.add(bread3);breadList.add(bread4);breadList.add(bread5);
    when(repository.findAll()).thenReturn(breadList);
    List<BreadProfile> expected = new ArrayList<>();
    expected.add(bread2); expected.add(bread3); expected.add(bread4);
    List<BreadProfile> returned = breadDAO.getByTitle("boller");
    assertArrayEquals(expected.toArray(), returned.toArray());
  }

  @Test void getAll()
  {
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    BreadProfile bread1 = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);
    List<BreadProfile> breadList = new ArrayList<>(); breadList.add(bread1);
    when(repository.findAll()).thenReturn(breadList);
    List<BreadProfile> returned = breadDAO.getAll();
    assertEquals(breadList, returned);
  }

  @Test void saveNewBread()
  {
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    BreadProfile bread1 = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);

    when(repository.save(any())).thenReturn(bread1);
    BreadProfile returned = breadDAO.saveNewBread(bread1);

    assertEquals(returned, bread1);
  }

  @Test void deleteBread()
  {
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    BreadProfile bread1 = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);
    Optional<BreadProfile> expected = Optional.of(bread1);
    when(repository.findById(any())).thenReturn(expected);
    BreadProfile returned = breadDAO.deleteBread(1L);
    assertEquals(expected.get(), returned);
  }

  @Test void updateBread()
  {
    List<BreadTarget> dummyTargets = new ArrayList<>();
    BreadTarget target = new BreadTarget("25", "25", "19-05-2023 12:30");
    dummyTargets.add(target);
    BreadProfile bread1 = new BreadProfile("Franskbrød", "fransk brød", dummyTargets);

    when(repository.save(any())).thenReturn(bread1);
    BreadProfile returned = breadDAO.updateBread(bread1);

    assertEquals(returned, bread1);
  }
}