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
  private String co2;
  private String offsetTime;

  public BreadTarget()
  {
  }

  public BreadTarget(String temp, String humidity, String co2, String offsetTime)
  {
    this.temp = temp;
    this.humidity = humidity;
    this.co2 = co2;
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

  public String getCo2()
  {
    return co2;
  }

  public void setCo2(String co2)
  {
    this.co2 = co2;
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
