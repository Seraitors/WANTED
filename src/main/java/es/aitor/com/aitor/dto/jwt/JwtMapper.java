package es.aitor.com.aitor.dto.jwt;


import es.aitor.com.aitor.Entidades.Registrar;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface JwtMapper {
    JwtSignupResponse toDto(Registrar entity);
    Registrar toEntity(JwtSignupRequest dto);

}
