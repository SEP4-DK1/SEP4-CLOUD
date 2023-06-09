package webapi.DAO;

import org.springframework.stereotype.Service;
import webapi.Domain.Target;
import webapi.Repositories.TargetRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

@Service
public class TargetDAOImpl implements TargetDAO
{
  private final TargetRepository targetRepository;

  public TargetDAOImpl(TargetRepository targetRepository){
    this.targetRepository = targetRepository;
  }

  @Override public List<Target> saveTargets(List<Target> target)
  {
    if(target!= null && target.size()>0){
      return targetRepository.saveAll(target);
    }
    return target;
  }

  @Override public Target getTarget()
  {
    List<Target> potentialTargetsToReturn = new ArrayList<>();
    List<Target> targets=targetRepository.findAll();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy k:mm:ss");
    TimeZone timeZone = TimeZone.getTimeZone("GMT+2");
    LocalDateTime currentTime = LocalDateTime.now(timeZone.toZoneId());
    for(Target t: targets){
      LocalDateTime targetTime = LocalDateTime.parse(t.getTimeToActivate(), formatter);
      if(targetTime.isBefore(currentTime)){
        potentialTargetsToReturn.add(t);
      }
    }

    System.out.println(Arrays.toString(potentialTargetsToReturn.toArray()));

    if(potentialTargetsToReturn.size()==1){
      Target toReturn = potentialTargetsToReturn.get(0);
      targetRepository.delete(toReturn);
      return toReturn;
    } else if(potentialTargetsToReturn.size()>1){
      Target toReturn = getNewestTarget(potentialTargetsToReturn);
      targetRepository.deleteAll(potentialTargetsToReturn);
      return toReturn;
    } else{
      return null;
    }
  }

  private Target getNewestTarget(List<Target> targets){
    LocalDateTime newestTime = LocalDateTime.of(1970, 1, 1, 1, 1, 1);
    Target toReturn = null;
    for(Target t: targets){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy k:mm:ss");
      LocalDateTime tTime = LocalDateTime.parse(t.getTimeToActivate(), formatter);
      if(tTime.isAfter(newestTime)){
        newestTime= tTime;
        toReturn = t;
      }
    }
    return toReturn;
  }
}
