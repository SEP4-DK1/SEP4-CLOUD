package webapi.Domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class BreadProfile
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;

  @OneToMany(cascade = {CascadeType.ALL})
  private List<BreadTarget> targets;

  public BreadProfile(String title, String description, List<BreadTarget> targets)
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

  public List<BreadTarget> getTargets()
  {
    return targets;
  }

  public void setTargets(List<BreadTarget> targets)
  {
    this.targets = targets;
  }
}
