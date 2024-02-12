package es.aitor.com.aitor.dto.Aplicacion;


import es.aitor.com.aitor.Entidades.Persona;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface PersonaMapper {

    PersonaResponseDto toDto(Persona entity);
    Persona toEntity(PersonaCreateDto dto);
    Persona toEntity(PersonaUpdateDto dto);

    List<PersonaResponseDto> toDtoList(List<Persona> list);
    //List<Mascota> toEntityList(List<MascotaDto> list);


}
