package com.ecommerce.app;

import com.ecommerce.service.*;
import com.ecommerce.domain.model.*;
import com.ecommerce.domain.repository.*;
import com.ecommerce.presentation.console.*;
import static com.ecommerce.utils.EntradaDatos.leerEntero;

import java.util.Scanner;


public class Main {

    static void main() {

        Scanner scanner = new Scanner(System.in);

        // Repositorios (Estructura de datos)
        Repositorio<Categoria> repoCategorias = new Repositorio<>();
        Repositorio<Producto> repoProductos = new Repositorio<>();
        Repositorio<Pedido> repoPedidos = new Repositorio<>();

        // Servicios (Lógica)
        CategoriaService categoriaService = new CategoriaService(repoCategorias, repoProductos);
        ProductoService productoService = new ProductoService(repoProductos, repoCategorias);
        PedidoService pedidoService = new PedidoService(repoPedidos, repoProductos);

        categoriaService.crear("Alimentos", "Estos productos tienen Vencimiento");
        categoriaService.crear("Electronica", "Estos productos tienen Garantía");

        // Menús (UI)
        MenuCategorias menuCategorias = new MenuCategorias(scanner, categoriaService);
        MenuProductos menuProductos = new MenuProductos(scanner, productoService);
        MenuPedidos menuPedidos = new MenuPedidos(scanner, pedidoService);

        // ===== MENÚ PRINCIPAL =====
        int opcion;

        do {
            System.out.println("\n==========================");
            System.out.println("    SISTEMA DE GESTIÓN    ");
            System.out.println("==========================");
            System.out.println("1. Gestionar productos");
            System.out.println("2. Gestionar pedidos");
            System.out.println("3. Gestionar categorías");
            System.out.println("4. Salir");
            System.out.println("==========================");

            opcion = leerEntero(scanner, "Elegir opción (1-4): ");

            switch (opcion) {
                case 1:
                    menuProductos.ejecutar();
                    break;
                case 2:
                    menuPedidos.ejecutar();
                    break;
                case 3:
                    menuCategorias.ejecutar();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (opcion != 4);

        scanner.close();
    }
}
