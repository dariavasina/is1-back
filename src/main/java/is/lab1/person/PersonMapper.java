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

    public static PersonDto toDto(Person entity) {
        if (entity == null) {
            return null;
        }
        return PersonDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .eyeColor(entity.getEyeColor())
                .hairColor(entity.getHairColor())
                .location(LocationMapper.toDto(entity.getLocation()))
                .weight(entity.getWeight())
                .build();
    }
}
