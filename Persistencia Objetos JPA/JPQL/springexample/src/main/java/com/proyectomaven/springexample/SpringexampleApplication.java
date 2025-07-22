package com.proyectomaven.springexample;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
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
    @Transactional
    public void run(String... args) {
        inicializarDatos();
        realizarOperacionesEjemplo();
    }

    private void inicializarDatos() {
        // Datos iniciales simplificados
        Clientes cliente = new Clientes("Pepe", "Pérez", "Calle Mayor 1");
        clientRepository.save(cliente);
    }

    private void realizarOperacionesEjemplo() {
        // 1. Buscar cliente (Read)
        Clientes cliente = clientRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // 2. Actualizar datos (Update)
        cliente.setDireccion("Nueva Dirección 456");
        clientRepository.save(cliente);

        // 3. Mostrar cambios
        System.out.println("Dirección actualizada: " + cliente.getDireccion());
    }
}
