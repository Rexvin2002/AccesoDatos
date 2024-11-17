package com.proyectomaven.springexample;

import com.proyectomaven.springexample.Controllers.EntityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner {

    @Autowired
    private EntityController entityController;  // Inyectando EntityController

    public static void main(String[] args) {
        SpringApplication.run(SpringexampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting initial setup...");

        // Llamar al método findDepartamentosWithFilters con parámetros de prueba
        entityController.findDepartamentosWithFilters("Sales", "Admin", "NYC")
                        .forEach(System.out::println);

        System.out.println("Setup completed!");
        System.exit(0);
    }

}
