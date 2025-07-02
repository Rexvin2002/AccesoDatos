package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyectomaven.springexample.Entities.Departamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    // Método personalizado con CriteriaBuilder
    @SuppressWarnings("unchecked")
    default List<Departamento> findByCriteria(
            EntityManager entityManager,
            String nomDeptoFilter,
            String ubicacionFilter,
            String presupuestoFilter,
            String orderByField,
            boolean orderAsc,
            boolean all,
            int maxResults,
            int firstResult) {
        // Usamos CriteriaBuilder para crear la consulta
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departamento> cq = cb.createQuery(Departamento.class);
        Root<Departamento> departamentoRoot = cq.from(Departamento.class);

        // Predicados (filtros)
        Predicate predicate = cb.conjunction(); // Crea una conjunción por defecto (true)

        if (nomDeptoFilter != null && !nomDeptoFilter.isEmpty()) {
            predicate = cb.and(predicate, cb.like(departamentoRoot.get("nomDepto"), "%" + nomDeptoFilter + "%"));
        }

        if (ubicacionFilter != null && !ubicacionFilter.isEmpty()) {
            predicate = cb.and(predicate, cb.like(departamentoRoot.get("ubicacion"), "%" + ubicacionFilter + "%"));
        }

        if (presupuestoFilter != null && !presupuestoFilter.isEmpty()) {
            predicate = cb.and(predicate, cb.like(departamentoRoot.get("presupuesto"), "%" + presupuestoFilter + "%"));
        }

        // Fecha de creación (greaterThan y currentDate)
        Predicate datePredicate = cb.greaterThan(departamentoRoot.get("fechaCreacion"), cb.currentDate());
        predicate = cb.and(predicate, datePredicate);

        // Establecer el predicado en la consulta
        cq.where(predicate);

        // Ordenar los resultados por el campo especificado
        if (orderByField != null && !orderByField.isEmpty()) {
            if (orderAsc) {
                cq.orderBy(cb.asc(departamentoRoot.get(orderByField)));
            } else {
                cq.orderBy(cb.desc(departamentoRoot.get(orderByField)));
            }
        }

        // Crear la consulta
        Query query = entityManager.createQuery(cq);

        // Paginación si es necesario
        if (!all) {
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
        }

        return query.getResultList();
    }
}
