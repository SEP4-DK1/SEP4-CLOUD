package webapi.Database;

import webapi.Target;

import java.util.List;

public interface TargetDAO
{
  void saveTargets(List<Target> target);
  Target getTarget();
}
