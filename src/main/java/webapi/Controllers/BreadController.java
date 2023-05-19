package webapi.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public List<BreadProfile> getBread(@RequestParam (required = false)Long id, @RequestParam (required = false) String title){
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
  @ResponseStatus(HttpStatus.CREATED)
  public BreadProfile newProfile(@RequestBody BreadProfile profile){
    return breadDAO.saveNewBread(profile);
  }

  @CrossOrigin
  @DeleteMapping("/bread")
  public ResponseEntity<?> deleteBread(@RequestParam long id){
    BreadProfile profile = breadDAO.deleteBread(id);
    if(profile==null){
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    else {
      return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);
    }
  }

  @CrossOrigin
  @PatchMapping("/bread")
  @ResponseStatus(HttpStatus.CREATED)
  public BreadProfile updateBread(@RequestBody BreadProfile profile){
    return breadDAO.updateBread(profile);
  }
}
