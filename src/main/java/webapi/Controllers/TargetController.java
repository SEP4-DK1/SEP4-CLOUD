package webapi.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import webapi.DAO.TargetDAO;
import webapi.Domain.Target;

import java.util.List;

@RestController
public class TargetController
{

  private final TargetDAO targetDAO;

  public TargetController(TargetDAO targetDAO){
    this.targetDAO = targetDAO;
  }

  @CrossOrigin
  @PostMapping("/target")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Target> newTarget(@RequestBody List<Target> newTarget){
    return targetDAO.saveTargets(newTarget);
  }

  @CrossOrigin
  @GetMapping("/target")
  public Target getTarget(){
    return targetDAO.getTarget();
  }
}
