package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyectomaven.springexample.Entities.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Método personalizado con CriteriaBuilder
    @SuppressWarnings("unchecked")
    default List<Empleado> findByCriteria(
            EntityManager entityManager,
            String nombreFilter,
            String apellidoFilter,
            Double salarioFilter,
            String fechaContratacionFilter,
            String orderByField,
            boolean orderAsc,
            boolean all,
            int maxResults,
            int firstResult) {

        // Usamos CriteriaBuilder para crear la consulta
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Empleado> cq = cb.createQuery(Empleado.class);
        Root<Empleado> empleadoRoot = cq.from(Empleado.class);

        // Predicados (filtros)
        Predicate predicate = cb.conjunction(); // Crea una conjunción por defecto (true)

        if (nombreFilter != null && !nombreFilter.isEmpty()) {
            predicate = cb.and(predicate, cb.like(empleadoRoot.get("nombre"), "%" + nombreFilter + "%"));
        }

        if (apellidoFilter != null && !apellidoFilter.isEmpty()) {
            predicate = cb.and(predicate, cb.like(empleadoRoot.get("apellido"), "%" + apellidoFilter + "%"));
        }

        if (salarioFilter != null) {
            predicate = cb.and(predicate, cb.equal(empleadoRoot.get("salario"), salarioFilter));
        }

        if (fechaContratacionFilter != null && !fechaContratacionFilter.isEmpty()) {
            predicate = cb.and(predicate, cb.equal(empleadoRoot.get("fechaContratacion"), fechaContratacionFilter));
        }

        // Establecer el predicado en la consulta
        cq.where(predicate);

        // Ordenar los resultados por el campo especificado
        if (orderByField != null && !orderByField.isEmpty()) {
            if (orderAsc) {
                cq.orderBy(cb.asc(empleadoRoot.get(orderByField)));
            } else {
                cq.orderBy(cb.desc(empleadoRoot.get(orderByField)));
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
