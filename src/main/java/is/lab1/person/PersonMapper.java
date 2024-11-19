package is.lab1.person;

import is.lab1.location.LocationMapper;

public class PersonMapper {
    public static Person toEntity(PersonDto dto) {
        if (dto == null) {
            return null;
        }
        return Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .eyeColor(dto.getEyeColor())
                .hairColor(dto.getHairColor())
                .location(LocationMapper.toEntity(dto.getLocation()))
                .weight(dto.getWeight())
                .build();
    }
}
