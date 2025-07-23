package com.proyectomaven.springexample.Criteria;

import com.proyectomaven.springexample.Entities.CategoriaProducto;
import com.proyectomaven.springexample.Entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class ProductoController {

    private EntityManager em;

    // Método base con búsqueda avanzada
    public List<Producto> findProductosAvanzados(
            boolean all,
            int maxResults,
            int firstResult,
            String nombreFilter,
            String descripcionFilter,
            Double precioMin,
            Double precioMax,
            Integer stockMin,
            Long idCategoria,
            String ordenarPor,
            boolean ordenDescendente,
            Date fechaCreacionDesde
    ) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
            Root<Producto> productoRoot = cq.from(Producto.class);

            // 1. Combinación de múltiples condiciones
            Predicate nombrePredicate = nombreFilter != null
                    ? cb.like(productoRoot.get("nombre"), "%" + nombreFilter + "%")
                    : cb.conjunction();

            Predicate descripcionPredicate = descripcionFilter != null
                    ? cb.like(productoRoot.get("descripcion"), "%" + descripcionFilter + "%")
                    : cb.conjunction();

            Predicate precioPredicate = cb.conjunction();
            if (precioMin != null && precioMax != null) {
                precioPredicate = cb.between(productoRoot.get("precio"), precioMin, precioMax);
            } else if (precioMin != null) {
                precioPredicate = cb.greaterThanOrEqualTo(productoRoot.get("precio"), precioMin);
            } else if (precioMax != null) {
                precioPredicate = cb.lessThanOrEqualTo(productoRoot.get("precio"), precioMax);
            }

            Predicate stockPredicate = stockMin != null
                    ? cb.greaterThanOrEqualTo(productoRoot.get("stock"), stockMin)
                    : cb.conjunction();

            Predicate categoriaPredicate = idCategoria != null
                    ? cb.equal(productoRoot.get("categoria").get("idCategoria"), idCategoria)
                    : cb.conjunction();

            // Combinación final con AND
            Predicate finalPredicate = cb.and(
                    nombrePredicate,
                    descripcionPredicate,
                    precioPredicate,
                    stockPredicate,
                    categoriaPredicate
            );

            // 2. Filtro por fecha usando greaterThan y currentDate
            if (fechaCreacionDesde != null) {
                Predicate fechaPredicate = cb.greaterThanOrEqualTo(
                        productoRoot.get("fechaCreacion"),
                        fechaCreacionDesde
                );
                finalPredicate = cb.and(finalPredicate, fechaPredicate);
            }

            // 3. ORDER BY dinámico
            if (ordenarPor != null) {
                if (ordenDescendente) {
                    cq.orderBy(cb.desc(productoRoot.get(ordenarPor)));
                } else {
                    cq.orderBy(cb.asc(productoRoot.get(ordenarPor)));
                }
            }

            cq.select(productoRoot).where(finalPredicate);

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

    // 4. Método con selectCase para clasificar productos por precio
    public List<Object[]> findProductosConClasificacionPrecio() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Producto> productoRoot = cq.from(Producto.class);

        Expression<Object> clasificacion = cb.selectCase()
                .when(cb.lessThan(productoRoot.get("precio"), 50.0), "Económico")
                .when(cb.between(productoRoot.get("precio"), 50.0, 100.0), "Medio")
                .otherwise("Premium");

        cq.multiselect(
                productoRoot.get("idProducto"),
                productoRoot.get("nombre"),
                productoRoot.get("precio"),
                clasificacion
        );

        cq.orderBy(cb.asc(productoRoot.get("precio")));

        return em.createQuery(cq).getResultList();
    }

    // 5. Método con Sum() y groupBy() para total de productos por categoría
    public List<Object[]> findTotalProductosPorCategoria() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Producto> productoRoot = cq.from(Producto.class);
        Join<Producto, CategoriaProducto> categoriaJoin = productoRoot.join("categoria", JoinType.LEFT);

        cq.multiselect(
                categoriaJoin.get("idCategoria"),
                categoriaJoin.get("nombre"),
                cb.count(productoRoot.get("idProducto")),
                cb.sum(productoRoot.get("stock"))
        );

        cq.groupBy(categoriaJoin.get("idCategoria"), categoriaJoin.get("nombre"));
        cq.orderBy(cb.desc(cb.count(productoRoot.get("idProducto"))));

        return em.createQuery(cq).getResultList();
    }
}
