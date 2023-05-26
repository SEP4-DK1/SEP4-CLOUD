package Controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webapi.Controllers.TargetController;
import webapi.DAO.TargetDAO;
import webapi.Domain.Target;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TargetControllerUnitTest
{

  @Mock
  private TargetDAO targetDAO;

  @InjectMocks
  private TargetController controller;

  @Test void newTarget()
  {
    List<Target> newTargets = new ArrayList<>();
    Target target1 = new Target("25", "25", "19-05-2023 12:45:00");
    Target target2 = new Target("30", "30", "19-05-2023 12:55:00");
    newTargets.add(target1); newTargets.add(target2);
    List<Target> returned;
    when(targetDAO.saveTargets(any())).thenReturn(newTargets);
    returned=controller.newTarget(newTargets);
    assertArrayEquals(newTargets.toArray(), returned.toArray());
  }

  @Test void getTarget()
  {
    Target target1 = new Target("25", "25", "19-05-2023 12:45:00");
    Target returned;
    when(targetDAO.getTarget()).thenReturn(target1);
    returned = controller.getTarget();
    assertEquals(target1, returned);
  }
}