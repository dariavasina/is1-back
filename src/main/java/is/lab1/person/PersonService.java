package is.lab1.person;

import is.lab1.location.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Person create(PersonDto dto) {
        Person person = Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .eyeColor(dto.getEyeColor())
                .hairColor(dto.getHairColor())
                .location(LocationMapper.toEntity(dto.getLocation()))
                .weight(dto.getWeight())
                .build();
        return personRepository.save(person);
    }

    public Person update(Person person) {
        return personRepository.save(person);
    }

    public void delete(Long id) {
        boolean exists = personRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Person with id " + id + " does not exist");
        }
        personRepository.deleteById(id);
    }
}
