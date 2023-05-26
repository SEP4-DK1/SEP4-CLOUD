package DAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webapi.DAO.TargetDAOImpl;
import webapi.Domain.Target;
import webapi.Repositories.TargetRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TargetDAOImplUnitTest
{

  @Mock
  private TargetRepository targetRepository;

  @InjectMocks
  private TargetDAOImpl targetDAO;

  @Test void saveTargets()
  {
    List<Target> toSave = new ArrayList<>();
    Target target1 = new Target("25", "25", "19-05-2023 12:45:00");
    Target target2 = new Target("30", "30", "19-05-2023 12:55:00");
    toSave.add(target1); toSave.add(target2);
    when(targetRepository.saveAll((any()))).thenReturn(toSave);
    List<Target> returned = targetDAO.saveTargets(toSave);
    assertArrayEquals(toSave.toArray(), returned.toArray());
  }

  @Test void saveTargetsNone()
  {
    List<Target> toSave = new ArrayList<>();
    List<Target> returned = targetDAO.saveTargets(toSave);
    assertEquals(toSave, returned);
  }

  @Test void saveTargetsNull()
  {
    List<Target> returned = targetDAO.saveTargets(null);
    assertNull(returned);
  }

  @Test void getTargetMany()
  {
    Target target1 = new Target("25", "25", "19-05-2023 12:45:00");
    Target target2 = new Target("30", "30", "19-05-2023 12:55:00");
    Target target3 = new Target("25", "25", "30-05-2030 12:56:00");
    Target target4 = new Target("30", "30", "19-05-2023 12:57:00");
    target1.setId(1); target2.setId(0); target3.setId(3); target4.setId(4);
    List<Target> targetList = new ArrayList<>();
    targetList.add(target1); targetList.add(target2); targetList.add(target3); targetList.add(target4);
    when(targetRepository.findAll()).thenReturn(targetList);
    Target returned = targetDAO.getTarget();
    System.out.println();
    assertEquals(target4, returned);
  }

  @Test void getTargetOne()
  {
    List<Target> targetList = new ArrayList<>();
    Target target1 = new Target("25", "25", "19-05-2023 12:45:00");
    targetList.add(target1);
    when(targetRepository.findAll()).thenReturn(targetList);
    Target returned = targetDAO.getTarget();
    assertEquals(target1, returned);
  }

  @Test void getTargetNone()
  {
    List<Target> targetList = new ArrayList<>();
    when(targetRepository.findAll()).thenReturn(targetList);
    Target returned = targetDAO.getTarget();
    assertNull(returned);
  }
}