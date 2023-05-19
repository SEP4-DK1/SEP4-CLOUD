package webapi.DAO;

import org.springframework.stereotype.Service;
import webapi.Domain.BreadProfile;
import webapi.Repositories.BreadRepository;

import java.util.ArrayList;
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
    List<BreadProfile> toReturn = new ArrayList<>();
    for(BreadProfile b:breadProfiles){
      if(b.getTitle().contains(title)){
        toReturn.add(b);
      }
    }
    return toReturn;
  }

  @Override public List<BreadProfile> getAll()
  {
    return breadRepository.findAll();
  }

  @Override public BreadProfile saveNewBread(BreadProfile breadProfile)
  {
    return breadRepository.save(breadProfile);
  }

  @Override public BreadProfile deleteBread(long id)
  {
    Optional<BreadProfile> profile = breadRepository.findById(id);
    if(profile.isPresent()){
      breadRepository.deleteById(id);
      return profile.get();
    } else{
      return null;
    }
  }

  @Override public BreadProfile updateBread(BreadProfile breadProfile)
  {
    breadRepository.deleteById(breadProfile.getId());
    return breadRepository.save(breadProfile);
  }
}
