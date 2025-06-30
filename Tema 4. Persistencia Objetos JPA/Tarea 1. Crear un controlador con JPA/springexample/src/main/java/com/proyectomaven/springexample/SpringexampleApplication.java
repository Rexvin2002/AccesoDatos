package com.proyectomaven.springexample;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.proyectomaven.springexample.Entities.Clientes;
import com.proyectomaven.springexample.Repositories.RepositorioClientes;

@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner {

    @Autowired
    private RepositorioClientes clientRepository;

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
        Clientes cl1 = new Clientes("Pepe", "Pérez", "Calle Mayor 1");
        Clientes cl2 = new Clientes("Juan", "López", "Calle Real 2");

        // Guardar uno a uno
        clientRepository.save(cl1);
        clientRepository.save(cl2);

        // Guardar todos juntos
        clientRepository.saveAll(List.of(cl1, cl2));

        // LECTURAS DE BASE DE DATOS
        Optional<Clientes> optionalClient = clientRepository.findById(1L);
        if (optionalClient.isPresent()) {
            System.out.println("Cliente encontrado: " + optionalClient.get());
        } else {
            System.out.println("Cliente con ID 1 no encontrado.");
        }

        // Leer todos los clientes
        List<Clientes> allClients = clientRepository.findAll();
        System.out.println("Todos los clientes:");
        allClients.forEach(System.out::println);

        // Actualizar un cliente
        if (optionalClient.isPresent()) {
            Clientes clienteActualizado = optionalClient.get();
            clienteActualizado.setDireccion("Nueva Dirección");
            clientRepository.save(clienteActualizado);
            System.out.println("Cliente actualizado: " + clienteActualizado);
        }

        // Eliminar un cliente
        clientRepository.deleteById(2L);
        System.out.println("Cliente con ID 2 eliminado.");
    }
}
