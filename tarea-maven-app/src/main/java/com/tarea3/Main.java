package com.tarea3;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClienteService clienteService = new ClienteService();
        CompraService compraService = new CompraService(clienteService);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Gestión de Clientes");
            System.out.println("2. Registrar Compra");
            System.out.println("3. Mostrar Puntos/Nivel");
            System.out.println("4. Salir");
            System.out.print("Opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    gestionClientes(clienteService, scanner);
                    break;
                case 2:
                    registrarCompra(compraService, scanner);
                    break;
                case 3:
                    mostrarPuntos(clienteService, compraService, scanner);
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private static void gestionClientes(ClienteService service, Scanner scanner) {
        //CRUD de clientes
        System.out.println("\n--- Gestión de Clientes ---");
        System.out.println("1. Agregar Cliente");
        System.out.println("2. Actualizar Cliente");
        System.out.println("3. Eliminar Cliente");
        System.out.println("4. Listar Clientes");
        System.out.print("Opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        switch (opcion) {
            case 1:
                System.out.print("ID: ");
                String id = scanner.nextLine();
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Correo: ");
                String correo = scanner.nextLine();
                System.out.print("Puntos: ");
                int puntos = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                System.out.print("Nivel (BRONCE, PLATA, ORO, PLATINO): ");
                String nivelStr = scanner.nextLine().toUpperCase();
                Nivel nivel = Nivel.valueOf(nivelStr);
                Cliente cliente = new Cliente(id, nombre, correo, puntos, nivel, 0);
                service.agregarCliente(cliente);
                break;
            case 2:
                //actualización id cliente
                System.out.print("ID del cliente a actualizar: ");
                String idActualizar = scanner.nextLine();
                if (!service.existeCliente(idActualizar)) {
                    System.out.println("Cliente no encontrado");
                    break;
                }
                System.out.print("Nuevo Nombre (dejar vacío para no cambiar): ");
                String nuevoNombre = scanner.nextLine();
                System.out.print("Nuevo Correo (dejar vacío para no cambiar): ");
                String nuevoCorreo = scanner.nextLine();
                service.actualizarCliente(idActualizar, nuevoNombre.isEmpty() ? null : nuevoNombre, 
                                          nuevoCorreo.isEmpty() ? null : nuevoCorreo);
                System.out.println("Cliente actualizado");
                break;
            case 3:
                // Lógica de eliminación de cliente por id
                System.out.print("ID del cliente a eliminar: ");
                String idEliminar = scanner.nextLine();
                if (service.eliminarCliente(idEliminar)) {
                    System.out.println("Cliente eliminado");
                } else {
                    System.out.println("Cliente no encontrado");
                }
                break;
            case 4:
                service.listarClientes().forEach(System.out::println);
                break;
            default:
                System.out.println("Opción inválida");
        }

    }

    private static void registrarCompra(CompraService service, Scanner scanner) {
        System.out.println("\n--- Registrar Compra ---");

        //Para sacar ID de cliente
        System.out.print("ID de Cliente: ");
        String idCliente = scanner.nextLine();

        // Validar cliente existente
        if (!service.getClienteService().existeCliente(idCliente)) {
            System.out.println("Cliente no encontrado");
            return;
        }

        // Sacar datos de la compra
        System.out.print("ID de Compra: ");
        String idCompra = scanner.nextLine();

        System.out.print("Monto: ");
        double monto;
        try {
            monto = scanner.nextDouble();
            if (monto <= 0) {
                System.out.println("El monto debe ser positivo");
                return;
            }
        } catch (Exception e) {
            System.out.println("Monto no válido");
            scanner.nextLine(); // Limpiar buffer
            return;
        }
        scanner.nextLine(); // Limpiar buffer

        // Obtener y validar fecha
        System.out.print("Fecha (YYYY-MM-DD): ");
        LocalDate fecha = obtenerFechaValida(scanner);
        if (fecha == null) return;

        // Crear y procesar compra
        Compra compra = new Compra(idCompra, idCliente, monto, fecha);
        procesarCompra(service, idCliente, compra);
    }

    // Métodos auxiliares requeridos
    private static LocalDate obtenerFechaValida(Scanner scanner) {
        try {
            LocalDate fecha = LocalDate.parse(scanner.nextLine());
            if (fecha.isAfter(LocalDate.now())) {
                System.out.println("La fecha no puede ser futura");
                return null;
            }
            return fecha;
        } catch (Exception e) {
            System.out.println("Formato de fecha no válido. Use YYYY-MM-DD");
            return null;
        }
    }

    private static void procesarCompra(CompraService service, String idCliente, Compra compra) {
        int puntos = service.calcularPuntos(compra);
        service.agregarCompra(compra);
        service.getClienteService().actualizarPuntos(idCliente, puntos);

        Cliente cliente = service.getClienteService().buscarCliente(idCliente);
        mostrarResumenCompra(compra, puntos, cliente);
    }

    // Método para mostrar resumen de compra

    private static void mostrarResumenCompra(Compra compra, int puntos, Cliente cliente) {
        System.out.println("\n--- Resumen de Compra ---");
        System.out.println("ID Compra: " + compra.getIdCompra());
        System.out.println("Monto: " + compra.getMonto());
        System.out.println("Fecha: " + compra.getFecha());
        System.out.println("Puntos ganados: " + puntos);
        System.out.println("Puntos totales: " + cliente.getPuntos());
        System.out.println("Nivel actual: " + cliente.getNivel());
    }

    // Método para mostrar puntos y nivel del cliente
   private static void mostrarPuntos(ClienteService clienteService, CompraService compraService, Scanner scanner) {
    System.out.println("\n--- Mostrar Puntos/Nivel ---");
    System.out.print("ID de Cliente: ");
    String idCliente = scanner.nextLine();
    
    // Validar cliente
        if (!clienteService.existeCliente(idCliente)) {
            System.out.println("Cliente no encontrado");
            return;
        }
    
    // Obtener datos
    Cliente cliente = clienteService.buscarCliente(idCliente);
    List<Compra> comprasCliente = compraService.getComprasPorCliente(idCliente);
    
    // Mostrar información
    mostrarInformacionCliente(cliente);
    mostrarResumenCompras(comprasCliente);
    }

// Métodos auxiliares para mostrar información del cliente y resumen de compras
private static void mostrarInformacionCliente(Cliente cliente) {
    System.out.println("\n--- Información del Cliente ---");
    System.out.println("Nombre: " + cliente.getNombre());
    System.out.println("ID: " + cliente.getId());
    System.out.println("Correo: " + cliente.getCorreo());
    System.out.println("Puntos: " + cliente.getPuntos());
    System.out.println("Nivel: " + cliente.getNivel());
    System.out.println("Streak de días: " + cliente.getStreakDias());
}

private static void mostrarResumenCompras(List<Compra> compras) {
    System.out.println("\n--- Historial de Compras ---");
    
    if (compras.isEmpty()) {
        System.out.println("No hay compras registradas");
        return;
    }
    
    // Mostrar detalle de cada compra
    compras.forEach(compra -> 
        System.out.printf("ID: %s | Monto: %.2f | Fecha: %s%n",
            compra.getIdCompra(),
            compra.getMonto(),
            compra.getFecha())
    );
    
    // Mostrar resumen
    double totalGastado = compras.stream()
        .mapToDouble(Compra::getMonto)
        .sum();
    
    System.out.printf("%nTotal de compras: %d%n", compras.size());
    System.out.printf("Total gastado: %.2f%n", totalGastado);
}
}