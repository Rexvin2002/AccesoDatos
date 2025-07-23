package com.proyectomaven.springexample.Criteria;

import com.proyectomaven.springexample.Entities.CategoriaProducto;
import com.proyectomaven.springexample.Entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import java.util.List;

public class CategoriaProductoController {

    private EntityManager em;

    // Método base con búsqueda avanzada
    public List<CategoriaProducto> findCategoriasAvanzadas(
            boolean all,
            int maxResults,
            int firstResult,
            String nombreFilter,
            String descripcionFilter,
            Integer minProductos,
            String ordenarPor,
            boolean ordenDescendente
    ) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CategoriaProducto> cq = cb.createQuery(CategoriaProducto.class);
            Root<CategoriaProducto> categoriaRoot = cq.from(CategoriaProducto.class);

            // 1. Combinación de múltiples condiciones
            Predicate nombrePredicate = nombreFilter != null
                    ? cb.like(categoriaRoot.get("nombre"), "%" + nombreFilter + "%")
                    : cb.conjunction();

            Predicate descripcionPredicate = descripcionFilter != null
                    ? cb.like(categoriaRoot.get("descripcion"), "%" + descripcionFilter + "%")
                    : cb.conjunction();

            // Predicado para contar productos asociados
            Predicate productosPredicate = cb.conjunction();
            if (minProductos != null) {
                Subquery<Long> subquery = cq.subquery(Long.class);
                Root<Producto> productoRoot = subquery.from(Producto.class);

                subquery.select(cb.count(productoRoot))
                        .where(cb.equal(productoRoot.get("categoria"), categoriaRoot));

                productosPredicate = cb.greaterThanOrEqualTo(subquery, minProductos.longValue());
            }

            // Combinación final con AND
            Predicate finalPredicate = cb.and(
                    nombrePredicate,
                    descripcionPredicate,
                    productosPredicate
            );

            // 2. ORDER BY dinámico
            if (ordenarPor != null) {
                if (ordenDescendente) {
                    cq.orderBy(cb.desc(categoriaRoot.get(ordenarPor)));
                } else {
                    cq.orderBy(cb.asc(categoriaRoot.get(ordenarPor)));
                }
            }

            cq.select(categoriaRoot).where(finalPredicate);

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

    // 3. Método con selectCase para clasificar categorías por cantidad de productos
    public List<Object[]> findCategoriasConClasificacion() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<CategoriaProducto> categoriaRoot = cq.from(CategoriaProducto.class);

        // Subconsulta para contar productos
        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Producto> productoRoot = subquery.from(Producto.class);

        subquery.select(cb.count(productoRoot))
                .where(cb.equal(productoRoot.get("categoria"), categoriaRoot));

        // Clasificación según cantidad de productos
        Expression<Object> clasificacion = cb.selectCase()
                .when(cb.lessThan(subquery, 5L), "Poca variedad")
                .when(cb.between(subquery, 5L, 15L), "Variedad media")
                .otherwise("Gran variedad");

        cq.multiselect(
                categoriaRoot.get("idCategoria"),
                categoriaRoot.get("nombre"),
                subquery,
                clasificacion
        );

        cq.orderBy(cb.desc(subquery));

        return em.createQuery(cq).getResultList();
    }

    // 4. Método con Sum() y groupBy() para productos por categoría
    public List<Object[]> findResumenCategorias() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<CategoriaProducto> categoriaRoot = cq.from(CategoriaProducto.class);
        Join<CategoriaProducto, Producto> productosJoin = categoriaRoot.join("productos", JoinType.LEFT);

        cq.multiselect(
                categoriaRoot.get("idCategoria"),
                categoriaRoot.get("nombre"),
                cb.count(productosJoin),
                cb.sum(cb.coalesce(productosJoin.get("stock"), 0))
        );

        cq.groupBy(categoriaRoot.get("idCategoria"), categoriaRoot.get("nombre"));
        cq.orderBy(cb.desc(cb.count(productosJoin)));

        return em.createQuery(cq).getResultList();
    }

    // 5. Método para categorías con productos de precio superior al promedio
    public List<CategoriaProducto> findCategoriasConProductosPremium() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoriaProducto> cq = cb.createQuery(CategoriaProducto.class);
        Root<CategoriaProducto> categoriaRoot = cq.from(CategoriaProducto.class);
        Join<CategoriaProducto, Producto> productosJoin = categoriaRoot.join("productos");

        // Subconsulta para precio promedio
        Subquery<Double> avgPriceSubquery = cq.subquery(Double.class);
        Root<Producto> productoAvgRoot = avgPriceSubquery.from(Producto.class);
        avgPriceSubquery.select(cb.avg(productoAvgRoot.get("precio")));

        cq.select(categoriaRoot)
                .where(cb.greaterThan(productosJoin.get("precio"), avgPriceSubquery))
                .distinct(true);

        return em.createQuery(cq).getResultList();
    }
}
