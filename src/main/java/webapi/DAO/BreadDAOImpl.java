package webapi.DAO;

import org.springframework.stereotype.Service;
import webapi.Domain.BreadProfile;
import webapi.Repositories.BreadRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BreadDAOImpl implements BreadDAO
{
  private final BreadRepository breadRepository;

  public BreadDAOImpl(BreadRepository breadRepository)
  {
    this.breadRepository = breadRepository;
  }

  @Override public Optional<BreadProfile> getById(long id)
  {
    return this.breadRepository.findById(id);
  }

  @Override public List<BreadProfile> getByTitle(String title)
  {
    List<BreadProfile> breadProfiles = breadRepository.findAll();
    for(BreadProfile b:breadProfiles){
      if(b.getTitle().contains(title)){
        breadProfiles.add(b);
      }
    }
    return breadProfiles;
  }

  @Override public List<BreadProfile> getAll()
  {
    return breadRepository.findAll();
  }

  @Override public BreadProfile saveNewBread(BreadProfile breadProfile)
  {
    return breadRepository.save(breadProfile);
  }

  @Override public void deleteBread(long id)
  {
    breadRepository.deleteById(id);
  }

  @Override public void updateBread(BreadProfile breadProfile)
  {
    breadRepository.deleteById(breadProfile.getId());
    breadRepository.save(breadProfile);
  }
}
