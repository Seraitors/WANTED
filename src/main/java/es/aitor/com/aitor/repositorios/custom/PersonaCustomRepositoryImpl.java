package es.aitor.com.aitor.repositorios.custom;

import es.aitor.com.aitor.Entidades.Persona;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class PersonaCustomRepositoryImpl implements PersonaCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Persona> findAllWithCriteria(Integer from, Integer to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Persona> cq = cb.createQuery(Persona.class);
        Root<Persona> countryRoot = cq.from(Persona.class);
        Path<Integer> precio = countryRoot.get("precio");

        List<Predicate> predicates = new ArrayList<>();
        if (from != null) {
            predicates.add(cb.greaterThanOrEqualTo(precio, from));
        }
        if (to != null) {
            predicates.add(cb.lessThanOrEqualTo(precio, to));
        }
        cq.select(countryRoot)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(cb.desc(precio));

        return entityManager.createQuery(cq).getResultList();
    }


}
