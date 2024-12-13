package com.proyectomaven.springexample;

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

import java.time.LocalDate;

@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringexampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting initial setup and testing...");

        // Create and save a Departamento
        Departamento departamento = new Departamento();
        departamento.setNomDepto("IT Department");
        departamento.setUbicacion("Headquarters");
        departamento.setPresupuesto(50000.0);
        departamentoRepository.save(departamento);

        System.out.println("Saved Departamento: " + departamento);

        // Create and save an Empleado
        Empleado empleado = new Empleado();
        empleado.setNombre("John");
        empleado.setApellido("Doe");
        empleado.setSalario(3500.0);
        empleado.setFechaContratacion(LocalDate.of(2023, 1, 15));
        empleado.setDepartamento(departamento);
        empleadoRepository.save(empleado);

        System.out.println("Saved Empleado: " + empleado);

        // Create and save a Proyecto
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("New Application");
        proyecto.setFechaInicio(LocalDate.of(2024, 1, 1));
        proyecto.setPresupuesto(10000.0);
        proyecto.setEstado("In Progress");
        proyectoRepository.save(proyecto);

        System.out.println("Saved Proyecto: " + proyecto);

        // Fetch and display data
        System.out.println("All Departamentos: " + departamentoRepository.findAll());
        System.out.println("All Empleados: " + empleadoRepository.findAll());
        System.out.println("All Proyectos: " + proyectoRepository.findAll());

        System.out.println("Setup and testing completed.");
    }
}
