package com.proyectomaven.springexample.Criteria;

import com.proyectomaven.springexample.Entities.Producto;
import com.proyectomaven.springexample.Entities.Proveedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import java.util.List;
import java.util.Set;

public class ProveedorController {
    
    private EntityManager em;
    
    // Método base con búsqueda avanzada
    public List<Proveedor> findProveedoresAvanzados(
        boolean all, 
        int maxResults, 
        int firstResult,
        String nombreFilter,
        String contactoFilter,
        Integer minProductos,
        String ordenarPor,
        boolean ordenDescendente
    ) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Proveedor> cq = cb.createQuery(Proveedor.class);
            Root<Proveedor> proveedorRoot = cq.from(Proveedor.class);
            
            // 1. Combinación de múltiples condiciones
            Predicate nombrePredicate = nombreFilter != null ? 
                cb.like(proveedorRoot.get("nombre"), "%" + nombreFilter + "%") : 
                cb.conjunction();
            
            Predicate contactoPredicate = contactoFilter != null ? 
                cb.like(proveedorRoot.get("contacto"), "%" + contactoFilter + "%") : 
                cb.conjunction();
            
            // Predicado para contar productos asociados
            Predicate productosPredicate = cb.conjunction();
            if (minProductos != null) {
                Subquery<Long> subquery = cq.subquery(Long.class);
                Root<Producto> productoRoot = subquery.from(Producto.class);
                Join<Producto, Proveedor> proveedoresJoin = productoRoot.join("proveedores");
                
                subquery.select(cb.count(productoRoot))
                       .where(cb.equal(proveedoresJoin, proveedorRoot));
                
                productosPredicate = cb.greaterThanOrEqualTo(subquery, minProductos.longValue());
            }
            
            // Combinación final con AND
            Predicate finalPredicate = cb.and(
                nombrePredicate,
                contactoPredicate,
                productosPredicate
            );
            
            // 2. ORDER BY dinámico
            if (ordenarPor != null) {
                if (ordenDescendente) {
                    cq.orderBy(cb.desc(proveedorRoot.get(ordenarPor)));
                } else {
                    cq.orderBy(cb.asc(proveedorRoot.get(ordenarPor)));
                }
            }
            
            cq.select(proveedorRoot).where(finalPredicate);
            
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
    
    // 3. Método con selectCase para clasificar proveedores por cantidad de productos
    public List<Object[]> findProveedoresConClasificacion() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Proveedor> proveedorRoot = cq.from(Proveedor.class);
        
        // Subconsulta para contar productos
        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Producto> productoRoot = subquery.from(Producto.class);
        Join<Producto, Proveedor> proveedoresJoin = productoRoot.join("proveedores");
        
        subquery.select(cb.count(productoRoot))
               .where(cb.equal(proveedoresJoin, proveedorRoot));
        
        // Clasificación según cantidad de productos
        Expression<Object> clasificacion = cb.selectCase()
            .when(cb.lessThan(subquery, 5L), "Pequeño")
            .when(cb.between(subquery, 5L, 10L), "Mediano")
            .otherwise("Grande");
        
        cq.multiselect(
            proveedorRoot.get("idProveedor"),
            proveedorRoot.get("nombre"),
            subquery,
            clasificacion
        );
        
        cq.orderBy(cb.desc(subquery));
        
        return em.createQuery(cq).getResultList();
    }
    
    // 4. Método con Sum() y groupBy() para productos por proveedor
    public List<Object[]> findResumenProveedores() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Proveedor> proveedorRoot = cq.from(Proveedor.class);
        Join<Proveedor, Producto> productosJoin = proveedorRoot.join("productos", JoinType.LEFT);
        
        cq.multiselect(
            proveedorRoot.get("idProveedor"),
            proveedorRoot.get("nombre"),
            cb.count(productosJoin),
            cb.sum(cb.coalesce(productosJoin.get("precio"), 0.0))
        );
        
        cq.groupBy(proveedorRoot.get("idProveedor"), proveedorRoot.get("nombre"));
        cq.orderBy(cb.desc(cb.count(productosJoin)));
        
        return em.createQuery(cq).getResultList();
    }
    
    // 5. Método para buscar proveedores con productos en un rango de precios
    public List<Proveedor> findProveedoresConProductosEnRangoPrecio(Double precioMin, Double precioMax) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Proveedor> cq = cb.createQuery(Proveedor.class);
        Root<Proveedor> proveedorRoot = cq.from(Proveedor.class);
        Join<Proveedor, Producto> productosJoin = proveedorRoot.join("productos");
        
        Predicate precioPredicate = cb.between(productosJoin.get("precio"), precioMin, precioMax);
        
        cq.select(proveedorRoot)
          .where(precioPredicate)
          .distinct(true);
        
        return em.createQuery(cq).getResultList();
    }
}