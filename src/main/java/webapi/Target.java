package webapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Target
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String temp;
  private String humidity;
  private String co2;
  private String timeToActivate;

  public Target()
  {
  }

  public Target(String temp, String humidity, String co2, String timeToActivate)
  {
    this.temp = temp;
    this.humidity = humidity;
    this.co2 = co2;
    this.timeToActivate = timeToActivate;
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

  public String getTimeToActivate()
  {
    return timeToActivate;
  }

  public void setTimeToActivate(String timeToActivate)
  {
    this.timeToActivate = timeToActivate;
  }
}
