package es.aitor.com.aitor.dto.Usuario;


import es.aitor.com.aitor.Entidades.Registrar;
import es.aitor.com.aitor.Entidades.Roles;
import es.aitor.com.aitor.dto.Usuario.PerfilSignupDto;
import es.aitor.com.aitor.dto.Usuario.UsuarioSignupDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface JpaMapper {
    //@Mapping(target = "empleados", ignore=true)
    UsuarioSignupDto toDto(Registrar entity);

    Registrar toEntity(UsuarioSignupDto dto);

    List<PerfilSignupDto> toDtoList(List<Roles> list);
    List<Roles> toEntityList(List<PerfilSignupDto> list);

    @Mapping(target = "perfil.usuarios", ignore = true)
    PerfilSignupDto toDto(Roles entity);

    Roles toEntity(PerfilSignupDto dto);

}
