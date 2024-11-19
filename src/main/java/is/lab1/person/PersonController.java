package is.lab1.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPeople() {
        return personService.getAllPeople();
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody PersonDto dto) {
        return new ResponseEntity<>(personService.create(dto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Person> update(@RequestBody Person person) {
        return new ResponseEntity<>(personService.update(person), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public HttpStatus deletePerson(@PathVariable("id") Long id) {
        personService.delete(id);
        return HttpStatus.OK;
    }
}
