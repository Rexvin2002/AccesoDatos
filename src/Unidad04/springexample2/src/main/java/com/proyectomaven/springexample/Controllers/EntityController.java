package com.proyectomaven.springexample.Controllers;

import com.proyectomaven.springexample.Entities.Departamento;
import com.proyectomaven.springexample.Entities.Empleado;
import com.proyectomaven.springexample.Entities.Proyecto;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.util.List;

import org.springframework.stereotype.Controller;

@Controller
public class EntityController {

    @PersistenceContext
    private EntityManager entityManager;  // Inyección de EntityManager

    // Método para buscar departamentos con filtros
    public List<Departamento> findDepartamentosWithFilters(String nomDepto1, String nomDepto2, String locationFilter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departamento> cq = cb.createQuery(Departamento.class);
        Root<Departamento> depRoot = cq.from(Departamento.class);

        // Filtros con LIKE y OR
        Predicate pred1 = cb.like(depRoot.get("nomDepto"), "%" + nomDepto1 + "%");
        Predicate pred2 = cb.like(depRoot.get("nomDepto"), "%" + nomDepto2 + "%");
        Predicate pred3 = cb.or(pred1, pred2);

        // Filtro por ubicación
        Predicate pred4 = cb.like(depRoot.get("location"), "%" + locationFilter + "%");

        // Combinación de filtros
        cq.select(depRoot).where(cb.and(pred3, pred4));

        // Ordenar resultados por nombre de departamento
        cq.orderBy(cb.asc(depRoot.get("nomDepto")));

        Query query = entityManager.createQuery(cq);

        // Ejecutar la consulta y devolver los resultados
        return query.getResultList();
    }

    // 2. Ordenar Proyectos por un campo dinámico
    public List<Proyecto> findProyectosOrderedBy(String orderByField) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Proyecto> cq = cb.createQuery(Proyecto.class);
        Root<Proyecto> projRoot = cq.from(Proyecto.class);

        cq.select(projRoot).orderBy(cb.asc(projRoot.get(orderByField)));

        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    // 3. Buscar empleados que se unieron después de la fecha actual
    public List<Empleado> findEmpleadosJoinedAfterCurrentDate() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Empleado> cq = cb.createQuery(Empleado.class);
        Root<Empleado> empRoot = cq.from(Empleado.class);

        Predicate datePredicate = cb.greaterThan(empRoot.get("fechaIngreso"), cb.currentDate());
        cq.select(empRoot).where(datePredicate);

        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    // 4. Usar lógica condicional con selectCase()
    public List<Object[]> findDepartamentosWithCustomCase() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Departamento> depRoot = cq.from(Departamento.class);

        Expression<Object> caseExpression = cb.selectCase()
            .when(cb.like(depRoot.get("nomDepto"), "Admin%"), "Administration")
            .when(cb.like(depRoot.get("nomDepto"), "Sales%"), "Sales Department")
            .otherwise("Other");

        cq.multiselect(depRoot.get("id"), depRoot.get("nomDepto"), caseExpression);

        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    // 5. Resumen de costes de proyectos usando sum() y groupBy()
    public List<Object[]> findProjectCostSummary() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Proyecto> projRoot = cq.from(Proyecto.class);

        cq.multiselect(
            projRoot.get("departamento").get("nomDepto"), 
            cb.sum(projRoot.get("coste"))
        ).groupBy(projRoot.get("departamento"));

        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
