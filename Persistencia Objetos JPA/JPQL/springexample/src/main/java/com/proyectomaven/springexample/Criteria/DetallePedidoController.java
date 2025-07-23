package com.proyectomaven.springexample.Criteria;

import com.proyectomaven.springexample.Entities.DetallePedido;
import com.proyectomaven.springexample.Entities.Pedido;
import com.proyectomaven.springexample.Entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import java.util.List;

public class DetallePedidoController {

    private EntityManager em;

    // Método base con búsqueda avanzada
    public List<DetallePedido> findDetallesAvanzados(
            boolean all,
            int maxResults,
            int firstResult,
            Long idPedido,
            Long idProducto,
            Integer cantidadMin,
            Integer cantidadMax,
            Double precioUnitarioMin,
            Double precioUnitarioMax,
            String ordenarPor,
            boolean ordenDescendente
    ) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<DetallePedido> cq = cb.createQuery(DetallePedido.class);
            Root<DetallePedido> detalleRoot = cq.from(DetallePedido.class);

            // 1. Combinación de múltiples condiciones
            Predicate pedidoPredicate = idPedido != null
                    ? cb.equal(detalleRoot.get("pedido").get("idPedido"), idPedido)
                    : cb.conjunction();

            Predicate productoPredicate = idProducto != null
                    ? cb.equal(detalleRoot.get("producto").get("idProducto"), idProducto)
                    : cb.conjunction();

            Predicate cantidadPredicate = cb.conjunction();
            if (cantidadMin != null && cantidadMax != null) {
                cantidadPredicate = cb.between(detalleRoot.get("cantidad"), cantidadMin, cantidadMax);
            } else if (cantidadMin != null) {
                cantidadPredicate = cb.greaterThanOrEqualTo(detalleRoot.get("cantidad"), cantidadMin);
            } else if (cantidadMax != null) {
                cantidadPredicate = cb.lessThanOrEqualTo(detalleRoot.get("cantidad"), cantidadMax);
            }

            Predicate precioPredicate = cb.conjunction();
            if (precioUnitarioMin != null && precioUnitarioMax != null) {
                precioPredicate = cb.between(detalleRoot.get("precioUnitario"), precioUnitarioMin, precioUnitarioMax);
            } else if (precioUnitarioMin != null) {
                precioPredicate = cb.greaterThanOrEqualTo(detalleRoot.get("precioUnitario"), precioUnitarioMin);
            } else if (precioUnitarioMax != null) {
                precioPredicate = cb.lessThanOrEqualTo(detalleRoot.get("precioUnitario"), precioUnitarioMax);
            }

            // Combinación final con AND
            Predicate finalPredicate = cb.and(
                    pedidoPredicate,
                    productoPredicate,
                    cantidadPredicate,
                    precioPredicate
            );

            // 2. ORDER BY dinámico
            if (ordenarPor != null) {
                if (ordenDescendente) {
                    cq.orderBy(cb.desc(detalleRoot.get(ordenarPor)));
                } else {
                    cq.orderBy(cb.asc(detalleRoot.get(ordenarPor)));
                }
            }

            cq.select(detalleRoot).where(finalPredicate);

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

    // 3. Método con selectCase para clasificar detalles por subtotal
    public List<Object[]> findDetallesConClasificacion() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<DetallePedido> detalleRoot = cq.from(DetallePedido.class);

        Expression<Double> subtotal = cb.prod(detalleRoot.get("cantidad"), detalleRoot.get("precioUnitario"));

        Expression<Object> clasificacion = cb.selectCase()
                .when(cb.lessThan(subtotal, 100.0), "Pequeño")
                .when(cb.between(subtotal, 100.0, 500.0), "Mediano")
                .otherwise("Grande");

        cq.multiselect(
                detalleRoot.get("idDetalle"),
                detalleRoot.get("pedido").get("idPedido"),
                detalleRoot.get("producto").get("nombre"),
                subtotal,
                clasificacion
        );

        cq.orderBy(cb.desc(subtotal));

        return em.createQuery(cq).getResultList();
    }

    // 4. Método con Sum() y groupBy() para resumen por producto
    public List<Object[]> findResumenProductosVendidos() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<DetallePedido> detalleRoot = cq.from(DetallePedido.class);
        Join<DetallePedido, Producto> productoJoin = detalleRoot.join("producto");

        Expression<Double> subtotal = cb.prod(detalleRoot.get("cantidad"), detalleRoot.get("precioUnitario"));

        cq.multiselect(
                productoJoin.get("idProducto"),
                productoJoin.get("nombre"),
                cb.sum(detalleRoot.get("cantidad")),
                cb.sum(subtotal)
        );

        cq.groupBy(productoJoin.get("idProducto"), productoJoin.get("nombre"));
        cq.orderBy(cb.desc(cb.sum(subtotal)));

        return em.createQuery(cq).getResultList();
    }

    // 5. Método para calcular total por pedido
    public List<Object[]> findTotalPorPedido() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<DetallePedido> detalleRoot = cq.from(DetallePedido.class);
        Join<DetallePedido, Pedido> pedidoJoin = detalleRoot.join("pedido");

        Expression<Double> subtotal = cb.prod(detalleRoot.get("cantidad"), detalleRoot.get("precioUnitario"));

        cq.multiselect(
                pedidoJoin.get("idPedido"),
                pedidoJoin.get("cliente").get("nombre"),
                cb.sum(subtotal)
        );

        cq.groupBy(pedidoJoin.get("idPedido"), pedidoJoin.get("cliente").get("nombre"));
        cq.orderBy(cb.desc(cb.sum(subtotal)));

        return em.createQuery(cq).getResultList();
    }
}
