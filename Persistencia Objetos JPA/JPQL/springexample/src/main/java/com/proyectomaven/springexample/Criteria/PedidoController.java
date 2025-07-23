package com.proyectomaven.springexample.Criteria;

import com.proyectomaven.springexample.Entities.Clientes;
import com.proyectomaven.springexample.Entities.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class PedidoController {

    private EntityManager em;

    // Método base con búsqueda avanzada
    public List<Pedido> findPedidosAvanzados(
            boolean all,
            int maxResults,
            int firstResult,
            Long idCliente,
            String estado,
            Date fechaDesde,
            Date fechaHasta,
            Double totalMinimo,
            Double totalMaximo,
            String ordenarPor,
            boolean ordenDescendente
    ) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Pedido> cq = cb.createQuery(Pedido.class);
            Root<Pedido> pedidoRoot = cq.from(Pedido.class);

            // 1. Combinación de múltiples condiciones
            Predicate clientePredicate = idCliente != null
                    ? cb.equal(pedidoRoot.get("cliente").get("idCliente"), idCliente)
                    : cb.conjunction();

            Predicate estadoPredicate = estado != null
                    ? cb.equal(pedidoRoot.get("estado"), estado)
                    : cb.conjunction();

            Predicate fechaPredicate = cb.conjunction();
            if (fechaDesde != null && fechaHasta != null) {
                fechaPredicate = cb.between(pedidoRoot.get("fechaPedido"), fechaDesde, fechaHasta);
            } else if (fechaDesde != null) {
                fechaPredicate = cb.greaterThanOrEqualTo(pedidoRoot.get("fechaPedido"), fechaDesde);
            } else if (fechaHasta != null) {
                fechaPredicate = cb.lessThanOrEqualTo(pedidoRoot.get("fechaPedido"), fechaHasta);
            }

            Predicate totalPredicate = cb.conjunction();
            if (totalMinimo != null && totalMaximo != null) {
                totalPredicate = cb.between(pedidoRoot.get("total"), totalMinimo, totalMaximo);
            } else if (totalMinimo != null) {
                totalPredicate = cb.greaterThanOrEqualTo(pedidoRoot.get("total"), totalMinimo);
            } else if (totalMaximo != null) {
                totalPredicate = cb.lessThanOrEqualTo(pedidoRoot.get("total"), totalMaximo);
            }

            // Combinación final con AND
            Predicate finalPredicate = cb.and(
                    clientePredicate,
                    estadoPredicate,
                    fechaPredicate,
                    totalPredicate
            );

            // 2. ORDER BY dinámico
            if (ordenarPor != null) {
                if (ordenDescendente) {
                    cq.orderBy(cb.desc(pedidoRoot.get(ordenarPor)));
                } else {
                    cq.orderBy(cb.asc(pedidoRoot.get(ordenarPor)));
                }
            }

            cq.select(pedidoRoot).where(finalPredicate);

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

    // 3. Método con selectCase para clasificar pedidos por monto
    public List<Object[]> findPedidosConClasificacion() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Pedido> pedidoRoot = cq.from(Pedido.class);

        Expression<Object> clasificacion = cb.selectCase()
                .when(cb.lessThan(pedidoRoot.get("total"), 100.0), "Pequeño")
                .when(cb.between(pedidoRoot.get("total"), 100.0, 500.0), "Mediano")
                .otherwise("Grande");

        cq.multiselect(
                pedidoRoot.get("idPedido"),
                pedidoRoot.get("cliente").get("nombre"),
                pedidoRoot.get("total"),
                clasificacion
        );

        cq.orderBy(cb.desc(pedidoRoot.get("total")));

        return em.createQuery(cq).getResultList();
    }

    // 4. Método con Sum() y groupBy() para total de pedidos por cliente
    public List<Object[]> findResumenPedidosPorCliente() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Pedido> pedidoRoot = cq.from(Pedido.class);
        Join<Pedido, Clientes> clienteJoin = pedidoRoot.join("cliente");

        cq.multiselect(
                clienteJoin.get("idCliente"),
                clienteJoin.get("nombre"),
                cb.count(pedidoRoot),
                cb.sum(cb.coalesce(pedidoRoot.get("total"), 0.0))
        );

        cq.groupBy(clienteJoin.get("idCliente"), clienteJoin.get("nombre"));
        cq.orderBy(cb.desc(cb.sum(pedidoRoot.get("total")))); // Fixed - added missing parenthesis

        return em.createQuery(cq).getResultList();
    }

    // 5. Método para comparar fechas con currentDate()
    public List<Pedido> findPedidosRecientes(int dias) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pedido> cq = cb.createQuery(Pedido.class);
        Root<Pedido> pedidoRoot = cq.from(Pedido.class);

        Expression<Date> fechaLimite = cb.function("DATE_SUB", Date.class,
                cb.currentDate(), cb.literal(dias));

        cq.select(pedidoRoot)
                .where(cb.greaterThanOrEqualTo(pedidoRoot.get("fechaPedido"), fechaLimite))
                .orderBy(cb.desc(pedidoRoot.get("fechaPedido")));

        return em.createQuery(cq).getResultList();
    }
}
