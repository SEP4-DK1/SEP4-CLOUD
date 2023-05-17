package webapi;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BreadProfile
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;

  @OneToMany
  private List<Target> targets;

  public BreadProfile(String title, String description, List<Target> targets)
  {
    this.title = title;
    this.description = description;
    this.targets = targets;
  }

  public BreadProfile()
  {
  }

  public Long getId()
  {
    return id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public List<Target> getTargets()
  {
    return targets;
  }

  public void setTargets(List<Target> targets)
  {
    this.targets = targets;
  }
}
