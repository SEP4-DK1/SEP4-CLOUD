package webapi.Controllers;

import org.springframework.web.bind.annotation.*;
import webapi.Domain.BreadProfile;
import webapi.DAO.BreadDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BreadController
{
  private final BreadDAO breadDAO;

  public BreadController(BreadDAO breadDAO){
    this.breadDAO = breadDAO;
  }

  @CrossOrigin
  @GetMapping("/bread")
  List<BreadProfile> getBread(@RequestParam (required = false)Long id, @RequestParam (required = false) String title){
    List<BreadProfile> toReturn = new ArrayList<>();
    if(id!=null){
      Optional<BreadProfile> breadProfile = breadDAO.getById(id);
      breadProfile.ifPresent(toReturn::add);
    } else if(title!=null){
      toReturn = breadDAO.getByTitle(title);
    } else{
      toReturn = breadDAO.getAll();
    }
    return toReturn;
  }

  @CrossOrigin
  @PostMapping("/bread")
  void newData(@RequestBody BreadProfile profile){
    breadDAO.saveNewBread(profile);
  }

  @CrossOrigin
  @DeleteMapping("/bread")
  void deleteBread(@RequestParam long id){
    breadDAO.deleteBread(id);
  }

  @CrossOrigin
  @PatchMapping("/data")
  void updateBread(@RequestBody BreadProfile profile){
    breadDAO.updateBread(profile);
  }
}
