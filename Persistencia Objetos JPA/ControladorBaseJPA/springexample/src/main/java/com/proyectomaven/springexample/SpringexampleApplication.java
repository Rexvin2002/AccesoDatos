package com.proyectomaven.springexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.proyectomaven.springexample.Entities.Clientes;
import com.proyectomaven.springexample.Repositories.RepositorioClientes;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner {

    @Autowired
    private RepositorioClientes clientRepository;
    
    private final Random random = new Random();

    public static void main(String[] args) {
        SpringApplication.run(SpringexampleApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("\n=== INICIO DE EJECUCIÓN ===");
        ejecutarCRUDRepetible();
        System.out.println("=== FIN DE EJECUCIÓN ===\n");
    }

    private void ejecutarCRUDRepetible() {
        try {
            // 1. Limpieza inicial segura (solo datos, no estructura)
            System.out.println("Preparando datos...");
            clientRepository.deleteAllInBatch(); // Más eficiente para limpieza
            
            // 2. CREATE - Crear nuevos clientes con nombres únicos dinámicos
            String nombreUnico1 = "Ana_" + random.nextInt(1000);
            String nombreUnico2 = "Luis_" + random.nextInt(1000);
            
            System.out.println("\nCreando clientes...");
            Clientes cliente1 = new Clientes(nombreUnico1, "García", "Calle Primavera 23");
            Clientes cliente2 = new Clientes(nombreUnico2, "Martínez", "Avenida Libertad 45");
            
            clientRepository.saveAll(List.of(cliente1, cliente2));
            System.out.println("Clientes creados: " + nombreUnico1 + " y " + nombreUnico2);

            // 3. READ - Mostrar todos los clientes
            System.out.println("\nClientes registrados:");
            imprimirClientes();

            // 4. UPDATE - Modificar un cliente
            System.out.println("\nActualizando cliente...");
            List<Clientes> clientes = clientRepository.findAll();
            if (!clientes.isEmpty()) {
                Clientes clienteActualizar = clientes.get(0);
                String nuevaDireccion = "Nueva Dirección " + random.nextInt(100);
                clienteActualizar.setDireccion(nuevaDireccion);
                clientRepository.save(clienteActualizar);
                System.out.println("Dirección actualizada a: " + nuevaDireccion);
            }

            // 5. DELETE - Eliminar un cliente
            System.out.println("\nEliminando cliente...");
            if (clientes.size() > 1) {
                Long idAEliminar = clientes.get(1).getIdEmpleado();
                clientRepository.deleteById(idAEliminar);
                System.out.println("Cliente con ID " + idAEliminar + " eliminado");
            }

            // 6. Resultado final
            System.out.println("\nEstado final de clientes:");
            imprimirClientes();
            
        } catch (Exception e) {
            System.err.println("Error durante la ejecución: " + e.getMessage());
        }
    }

    private void imprimirClientes() {
        List<Clientes> clientes = clientRepository.findAll();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            clientes.forEach(c -> System.out.println(
                "ID: " + c.getIdEmpleado() + 
                " - " + c.getNombre() + " " + c.getApellido() +
                " - Dirección: " + c.getDireccion()));
        }
    }
}