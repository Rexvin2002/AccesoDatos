package com.proyectomaven.springexample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.proyectomaven.springexample.Entities.Departamento;
import com.proyectomaven.springexample.Entities.Empleado;
import com.proyectomaven.springexample.Entities.Proyecto;
import com.proyectomaven.springexample.Repositories.DepartamentoRepository;
import com.proyectomaven.springexample.Repositories.EmpleadoRepository;
import com.proyectomaven.springexample.Repositories.ProyectoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(SpringexampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        /**
         * DEPARTAMENTO
         */

        // Parámetros de filtrado y paginación
        String nomDeptoFilter = "IT"; // Ejemplo de filtro por nombre de departamento
        String ubicacionFilter = "Madrid"; // Ejemplo de filtro por ubicación
        String presupuestoFilter = "50000"; // Ejemplo de filtro por presupuesto
        String orderByField = "presupuesto"; // Campo por el que ordenar
        boolean orderAsc = true; // Orden ascendente
        boolean all = false; // Si es true, devuelve todos los resultados
        int maxResults = 10; // Número máximo de resultados
        int firstResult = 0; // Primer resultado de la paginación

        // Llamada al método findByCriteria
        List<Departamento> departamentos = departamentoRepository.findByCriteria(
                entityManager,
                nomDeptoFilter,
                ubicacionFilter,
                presupuestoFilter,
                orderByField,
                orderAsc,
                all,
                maxResults,
                firstResult);

        // Mostrar los departamentos obtenidos
        departamentos.forEach(departamento -> {
            System.out.println(departamento);
        });

        /**
         * EMPLEADO
         */

        // Parámetros de filtrado y paginación
        String nombreFilter = "Juan"; // Ejemplo de filtro por nombre
        String apellidoFilter = "Pérez"; // Ejemplo de filtro por apellido
        Double salarioFilter = 3000.0; // Ejemplo de filtro por salario
        String fechaContratacionFilter = "2023-01-01"; // Ejemplo de filtro por fecha de contratación
        String orderByField2 = "salario"; // Campo por el que ordenar
        boolean orderAsc2 = true; // Orden ascendente
        boolean all2 = false; // Si es true, devuelve todos los resultados
        int maxResults2 = 10; // Número máximo de resultados
        int firstResult2 = 0; // Primer resultado de la paginación

        // Llamada al método findByCriteria
        List<Empleado> empleados = empleadoRepository.findByCriteria(
                entityManager,
                nombreFilter,
                apellidoFilter,
                salarioFilter,
                fechaContratacionFilter,
                orderByField2,
                orderAsc2,
                all2,
                maxResults2,
                firstResult2);

        // Mostrar los empleados obtenidos
        empleados.forEach(empleado -> {
            System.out.println(empleado);
        });

        /**
         * PROYECTO
         */

        // Parámetros de filtrado y paginación
        String nombreFilter2 = "Proyecto A"; // Filtro por nombre de proyecto
        Double presupuestoFilter2 = 50000.0; // Filtro por presupuesto
        String estadoFilter = "En progreso"; // Filtro por estado
        String fechaInicioFilter = "2023-01-01"; // Filtro por fecha de inicio
        String orderByField3 = "presupuesto"; // Ordenar por presupuesto
        boolean orderAsc3 = true; // Orden ascendente
        boolean all3 = false; // No traer todos los resultados
        int maxResults3 = 10; // Número máximo de resultados
        int firstResult3 = 0; // Primer resultado de la paginación

        // Llamada al método findByCriteria
        List<Proyecto> proyectos = proyectoRepository.findByCriteria(
                entityManager,
                nombreFilter2,
                presupuestoFilter2,
                estadoFilter,
                fechaInicioFilter,
                orderByField3,
                orderAsc3,
                all3,
                maxResults3,
                firstResult3);

        // Mostrar los proyectos obtenidos
        proyectos.forEach(proyecto -> {
            System.out.println(proyecto);
        });
    }
}
