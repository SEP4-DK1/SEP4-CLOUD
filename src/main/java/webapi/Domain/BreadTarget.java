package webapi.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BreadTarget
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String temp;
  private String humidity;
  private String offsetTime;

  public BreadTarget()
  {
  }

  public BreadTarget(String temp, String humidity, String offsetTime)
  {
    this.temp = temp;
    this.humidity = humidity;
    this.offsetTime = offsetTime;
  }

  public Long getId()
  {
    return id;
  }

  public String getTemp()
  {
    return temp;
  }

  public void setTemp(String temp)
  {
    this.temp = temp;
  }

  public String getHumidity()
  {
    return humidity;
  }

  public void setHumidity(String humidity)
  {
    this.humidity = humidity;
  }

  public String getOffset()
  {
    return offsetTime;
  }

  public void setOffset(String offsetTime)
  {
    this.offsetTime = offsetTime;
  }
}
