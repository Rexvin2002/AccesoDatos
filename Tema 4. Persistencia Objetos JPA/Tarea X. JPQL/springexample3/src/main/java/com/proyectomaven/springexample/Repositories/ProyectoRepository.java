package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyectomaven.springexample.Entities.Proyecto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    // Método personalizado con CriteriaBuilder para proyectos
    @SuppressWarnings("unchecked")
    default List<Proyecto> findByCriteria(
            EntityManager entityManager,
            String nombreFilter,
            Double presupuestoFilter,
            String estadoFilter,
            String fechaInicioFilter,
            String orderByField,
            boolean orderAsc,
            boolean all,
            int maxResults,
            int firstResult) {

        // Usamos CriteriaBuilder para crear la consulta
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Proyecto> cq = cb.createQuery(Proyecto.class);
        Root<Proyecto> proyectoRoot = cq.from(Proyecto.class);

        // Predicados (filtros)
        Predicate predicate = cb.conjunction(); // Crea una conjunción por defecto (true)

        // Filtramos según los parámetros proporcionados
        if (nombreFilter != null && !nombreFilter.isEmpty()) {
            predicate = cb.and(predicate, cb.like(proyectoRoot.get("nombre"), "%" + nombreFilter + "%"));
        }

        if (presupuestoFilter != null) {
            predicate = cb.and(predicate, cb.equal(proyectoRoot.get("presupuesto"), presupuestoFilter));
        }

        if (estadoFilter != null && !estadoFilter.isEmpty()) {
            predicate = cb.and(predicate, cb.like(proyectoRoot.get("estado"), "%" + estadoFilter + "%"));
        }

        if (fechaInicioFilter != null && !fechaInicioFilter.isEmpty()) {
            predicate = cb.and(predicate, cb.equal(proyectoRoot.get("fechaInicio"), fechaInicioFilter));
        }

        // Establecemos el predicado (filtro) en la consulta
        cq.where(predicate);

        // Ordenar por el campo especificado
        if (orderByField != null && !orderByField.isEmpty()) {
            if (orderAsc) {
                cq.orderBy(cb.asc(proyectoRoot.get(orderByField)));
            } else {
                cq.orderBy(cb.desc(proyectoRoot.get(orderByField)));
            }
        }

        // Crear la consulta con los filtros y orden
        Query query = entityManager.createQuery(cq);

        // Aplicar la paginación si es necesario
        if (!all) {
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
        }

        return query.getResultList();
    }
}
