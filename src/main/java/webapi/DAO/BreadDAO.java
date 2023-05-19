package webapi.DAO;

import webapi.Domain.BreadProfile;

import java.util.List;
import java.util.Optional;

public interface BreadDAO
{

  public Optional<BreadProfile> getById(long id);
  public List<BreadProfile> getByTitle(String title);
  public List<BreadProfile> getAll();
  public BreadProfile saveNewBread(BreadProfile breadProfile);
  public BreadProfile deleteBread(long id);
  public BreadProfile updateBread(BreadProfile breadProfile);

}
