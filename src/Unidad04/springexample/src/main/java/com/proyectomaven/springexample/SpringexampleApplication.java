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

@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner{

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

        // Guardar uno a uno
        clienteRepository.save(cl1);
        clienteRepository.save(cl2);

        // Guardar varios juntos
        Cliente cl3 = new Cliente("Ana", "Martínez", "Plaza España 3");
        Cliente cl4 = new Cliente("María", "Sánchez", "Avenida Libertad 4");
        clienteRepository.saveAll(List.of(cl3, cl4));

        // LECTURAS DE BASE DE DATOS
        System.out.println("\n--- Búsqueda por ID ---");
        Optional<Cliente> optionalClient = clienteRepository.findById(1L);
        optionalClient.ifPresent(cliente -> 
            System.out.println("Cliente encontrado: " + cliente.getNombre())
        );

        System.out.println("\n--- Listado de todos los clientes ---");
        List<Cliente> todos = clienteRepository.findAll();
        todos.forEach(cliente -> 
            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido())
        );
    }

}
