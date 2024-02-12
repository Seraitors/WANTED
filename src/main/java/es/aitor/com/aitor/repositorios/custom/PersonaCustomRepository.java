package es.aitor.com.aitor.repositorios.custom;

import es.aitor.com.aitor.Entidades.Persona;


import java.util.List;

public interface PersonaCustomRepository {

    public List<Persona> findAllWithCriteria(Integer from, Integer to);
}
