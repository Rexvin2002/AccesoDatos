package com.proyectomaven.springexample.Criteria;

import com.proyectomaven.springexample.Entities.Clientes;
import com.proyectomaven.springexample.Entities.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class ClientesController {

    private EntityManager em;

    // Método base modificado con operaciones avanzadas
    public List<Clientes> findClientesAdvanced(
            boolean all,
            int maxResults,
            int firstResult,
            String nombreFilter,
            String apellidoFilter,
            String direccionFilter,
            String ordenarPor,
            boolean ordenDescendente,
            Date fechaMinima
    ) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Clientes> cq = cb.createQuery(Clientes.class);
            Root<Clientes> clienteRoot = cq.from(Clientes.class);

            // 1. Varios OR, AND, LIKE mezclados
            Predicate nombrePredicate = nombreFilter != null
                    ? cb.like(clienteRoot.get("nombre"), "%" + nombreFilter + "%")
                    : cb.conjunction();

            Predicate apellidoPredicate = apellidoFilter != null
                    ? cb.like(clienteRoot.get("apellido"), "%" + apellidoFilter + "%")
                    : cb.conjunction();

            Predicate direccionPredicate = direccionFilter != null
                    ? cb.like(clienteRoot.get("direccion"), "%" + direccionFilter + "%")
                    : cb.conjunction();

            // Combinación de predicados con AND
            Predicate finalPredicate = cb.and(
                    nombrePredicate,
                    apellidoPredicate,
                    direccionPredicate
            );

            // 2. ORDER BY dinámico
            if (ordenarPor != null) {
                if (ordenDescendente) {
                    cq.orderBy(cb.desc(clienteRoot.get(ordenarPor)));
                } else {
                    cq.orderBy(cb.asc(clienteRoot.get(ordenarPor)));
                }
            }

            // 3. greaterThan y currentDate() para fechas
            if (fechaMinima != null) {
                Predicate fechaPredicate = cb.greaterThan(
                        clienteRoot.get("fechaRegistro"),
                        fechaMinima
                );
                finalPredicate = cb.and(finalPredicate, fechaPredicate);
            }

            cq.select(clienteRoot).where(finalPredicate);

            Query q = em.createQuery(cq);

            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // 4. Método con selectCase (Opcional)
    public List<Object[]> findClientesConCategoria(Date fechaLimite) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Clientes> clienteRoot = cq.from(Clientes.class);

        Expression<Object> categoria = cb.selectCase()
                .when(cb.greaterThan(clienteRoot.get("fechaRegistro"), fechaLimite), "Nuevo")
                .otherwise("Antiguo");

        cq.multiselect(clienteRoot.get("idCliente"), clienteRoot.get("nombre"), categoria);
        cq.orderBy(cb.asc(clienteRoot.get("nombre")));

        return em.createQuery(cq).getResultList();
    }

    // 5. Método con Sum() y groupBy() (Opcional)
    // Asumiendo que tenemos acceso a la entidad Pedido y su relación con Clientes
    public List<Object[]> findTotalPedidosPorCliente() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Clientes> clienteRoot = cq.from(Clientes.class);
        Join<Clientes, Pedido> pedidosJoin = clienteRoot.join("pedidos", JoinType.LEFT);

        cq.multiselect(
                clienteRoot.get("idCliente"),
                clienteRoot.get("nombre"),
                cb.sum(cb.coalesce(pedidosJoin.get("total"), 0))
        );

        cq.groupBy(clienteRoot.get("idCliente"), clienteRoot.get("nombre"));
        cq.orderBy(cb.desc(cb.sum(pedidosJoin.get("total"))));

        return em.createQuery(cq).getResultList();
    }
}
