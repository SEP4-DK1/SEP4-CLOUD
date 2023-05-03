package webapi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataController {
    private final DataRepository repository;

    DataController(DataRepository repository){
        this.repository = repository;
    }

    @CrossOrigin
    @GetMapping("/data")
    List<Data> all(){
        return repository.findAll();
    }

    @CrossOrigin
    @PostMapping("/data")
    Data newData(@RequestBody Data newData){
        return repository.save(newData);
    }

    @CrossOrigin
    @GetMapping("/data/{id}")
    Data one(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }
}
