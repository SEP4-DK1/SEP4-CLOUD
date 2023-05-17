package webapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import webapi.Database.TargetDAO;

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
  void newTarget(@RequestBody List<Target> newTarget){
    targetDAO.saveTargets(newTarget);
  }

  @CrossOrigin
  @GetMapping("/target")
  Target getTarget(){
    return targetDAO.getTarget();
  }
}
