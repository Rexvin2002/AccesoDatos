package com.proyectomaven.springexample.Criteria;

import com.proyectomaven.springexample.Entities.CarritoCompras;
import com.proyectomaven.springexample.Entities.Clientes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class CarritoComprasController {

    private EntityManager em;

    // Método base con búsqueda avanzada
    public List<CarritoCompras> findCarritosAvanzados(
            boolean all,
            int maxResults,
            int firstResult,
            Long idCliente,
            String nombreCliente,
            String ordenarPor,
            boolean ordenDescendente
    ) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CarritoCompras> cq = cb.createQuery(CarritoCompras.class);
            Root<CarritoCompras> carritoRoot = cq.from(CarritoCompras.class);
            Join<CarritoCompras, Clientes> clienteJoin = carritoRoot.join("cliente");

            // 1. Combinación de múltiples condiciones
            Predicate clientePredicate = idCliente != null
                    ? cb.equal(clienteJoin.get("idCliente"), idCliente)
                    : cb.conjunction();

            Predicate nombrePredicate = nombreCliente != null
                    ? cb.like(clienteJoin.get("nombre"), "%" + nombreCliente + "%")
                    : cb.conjunction();

            // Combinación final con AND
            Predicate finalPredicate = cb.and(
                    clientePredicate,
                    nombrePredicate
            );

            // 2. ORDER BY dinámico
            if (ordenarPor != null) {
                Path<?> orderPath;
                if (ordenarPor.equals("cliente")) {
                    orderPath = clienteJoin.get("nombre");
                } else {
                    orderPath = carritoRoot.get(ordenarPor);
                }

                if (ordenDescendente) {
                    cq.orderBy(cb.desc(orderPath));
                } else {
                    cq.orderBy(cb.asc(orderPath));
                }
            }

            cq.select(carritoRoot).where(finalPredicate);

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

    // 3. Método con selectCase para clasificar carritos por antigüedad del cliente
    public List<Object[]> findCarritosConClasificacionCliente(Date fechaLimite) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<CarritoCompras> carritoRoot = cq.from(CarritoCompras.class);
        Join<CarritoCompras, Clientes> clienteJoin = carritoRoot.join("cliente");

        Expression<Object> clasificacion = cb.selectCase()
                .when(cb.greaterThan(clienteJoin.get("fechaRegistro"), fechaLimite), "Cliente nuevo")
                .otherwise("Cliente antiguo");

        cq.multiselect(
                carritoRoot.get("idCarrito"),
                clienteJoin.get("nombre"),
                clienteJoin.get("fechaRegistro"),
                clasificacion
        );

        cq.orderBy(cb.desc(clienteJoin.get("fechaRegistro")));

        return em.createQuery(cq).getResultList();
    }

    // 4. Método con join para obtener carritos con información de cliente
    public List<Object[]> findCarritosConInfoCliente() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<CarritoCompras> carritoRoot = cq.from(CarritoCompras.class);
        Join<CarritoCompras, Clientes> clienteJoin = carritoRoot.join("cliente");

        cq.multiselect(
                carritoRoot.get("idCarrito"),
                clienteJoin.get("idCliente"),
                clienteJoin.get("nombre"),
                clienteJoin.get("email")
        );

        cq.orderBy(cb.asc(clienteJoin.get("nombre")));

        return em.createQuery(cq).getResultList();
    }

    // 5. Método para contar carritos por tipo de cliente
    public List<Object[]> countCarritosPorTipoCliente(Date fechaLimite) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<CarritoCompras> carritoRoot = cq.from(CarritoCompras.class);
        Join<CarritoCompras, Clientes> clienteJoin = carritoRoot.join("cliente");

        Expression<Object> tipoCliente = cb.selectCase()
                .when(cb.greaterThan(clienteJoin.get("fechaRegistro"), fechaLimite), "Nuevo")
                .otherwise("Antiguo");

        cq.multiselect(
                tipoCliente,
                cb.count(carritoRoot)
        );

        cq.groupBy(tipoCliente);

        return em.createQuery(cq).getResultList();
    }
}
