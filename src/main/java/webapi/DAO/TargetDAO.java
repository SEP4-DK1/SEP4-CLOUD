package webapi.DAO;

import webapi.Domain.Target;

import java.util.List;

public interface TargetDAO
{
  List<Target> saveTargets(List<Target> target);
  Target getTarget();
}
