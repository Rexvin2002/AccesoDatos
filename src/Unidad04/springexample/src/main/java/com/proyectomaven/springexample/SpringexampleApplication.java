package com.proyectomaven.springexample;

import com.proyectomaven.springexample.Entities.Cliente;
import com.proyectomaven.springexample.Repositories.RepositorioClientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(SpringexampleApplication.class);

    @Autowired
    private RepositorioClientes clienteRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringexampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        funcionPruebas1();
    }

    @Transactional
    public void funcionPruebas1() {
        // ESCRITURA EN BBDD
        Cliente cl1 = new Cliente("Pepe", "García", "Calle Mayor 1");
        Cliente cl2 = new Cliente("Juan", "López", "Calle Menor 2");
        clienteRepository.save(cl1);
        clienteRepository.save(cl2);

        Cliente cl3 = new Cliente("Ana", "Martínez", "Plaza España 3");
        Cliente cl4 = new Cliente("María", "Sánchez", "Avenida Libertad 4");
        clienteRepository.saveAll(List.of(cl3, cl4));

        // LECTURAS DE BASE DE DATOS
        logger.info("\n\n\n--- Búsqueda por ID ---");
        Optional<Cliente> optionalClient = clienteRepository.findById(1L);
        optionalClient.ifPresent(cliente -> logger.info("Cliente encontrado: {}", cliente.getNombre()));

        logger.info("\n\n\n--- Listado de todos los clientes ---");
        List<Cliente> todos = clienteRepository.findAll();
        todos.forEach(cliente -> logger.info("Cliente: {} {}", cliente.getNombre(), cliente.getApellido()));
        
        // Finalizar la aplicación
        // System.exit(0);
    }

}
