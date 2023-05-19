package webapi.Domain;

public class SearchObject
{
  private String fromDate;
  private String toDate;

  public SearchObject(String fromDate, String toDate)
  {
    this.fromDate = fromDate;
    this.toDate = toDate;
  }

  public SearchObject()
  {
  }

  public boolean isNotEmptyOrBlank(){
    return fromDate != null && !fromDate.isBlank() && toDate != null && !toDate.isBlank();
  }

  public String getFromDate()
  {
    return fromDate;
  }

  public void setFromDate(String fromDate)
  {
    this.fromDate = fromDate;
  }

  public String getToDate()
  {
    return toDate;
  }

  public void setToDate(String toDate)
  {
    this.toDate = toDate;
  }
}
