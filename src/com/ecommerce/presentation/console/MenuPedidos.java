package com.ecommerce.presentation.console;

import com.ecommerce.domain.model.Pedido;
import com.ecommerce.service.PedidoService;
import com.ecommerce.domain.model.EstadoPedido;
import static com.ecommerce.utils.EntradaDatos.*;
import com.ecommerce.domain.exception.ProductoNoEncontradoException;
import java.util.Scanner;

public class MenuPedidos extends Menu {

    private final PedidoService pedidoService;

    public MenuPedidos(Scanner scanner, PedidoService pedidoService) {
        super(scanner);
        this.pedidoService = pedidoService;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ PEDIDOS ---");
        System.out.println("1 - Crear pedido");
        System.out.println("2 - Listar pedidos");
        System.out.println("3 - Consultar pedido");
        System.out.println("4 - Agregar producto a pedido");
        System.out.println("5 - Cambiar estado de pedido");
        System.out.println("6 - Eliminar pedido");
        System.out.println("7 - Volver");
        System.out.println("----------------------");
    }

    @Override
    public void ejecutar() {

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Elija una opción (1-7): ");

            switch (opcion) {

                case 1:
                    crear();
                    break;

                case 2:
                    listar();
                    break;

                case 3:
                    buscarPorCodigo();
                    break;

                case 4:
                    int c = leerEntero(scanner, "Código del pedido: ");
                    Pedido p = pedidoService.buscarPorCodigo(c);

                    agregarProducto(p);
                    break;

                case 5:
                    cambiarEstado();
                    break;

                case 6:
                    eliminar();
                    break;

                case 7:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Error: Opción inválida");
            }

        } while (opcion != 7);
    }

    @Override
    protected void crear() {

        boolean seguir = true;
        Pedido pedido = pedidoService.crearPedido();
        System.out.println("Pedido creado correctamente: #" + pedido.getCodigo());

        do {
            if (leerSiNo(scanner, "¿Desea agregar un producto al pedido?")) {
                agregarProducto(pedido);

            } else {
                seguir = false;
            }
        } while (seguir);

        System.out.println("Pedido finalizado: #" + pedido.getCodigo());
    }

    @Override
    protected void listar() {

        mostrarLista(pedidoService.listar());
    }

    @Override
    protected void buscarPorCodigo() {

        int codigo = leerEntero(scanner, "Ingrese código del pedido: ");

        try {
            Pedido pedido = pedidoService.buscarPorCodigo(codigo);
            System.out.println(pedido);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void agregarProducto(Pedido p) {

        System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
        pedidoService.listar_productos();

        try {
            int codigoProducto = leerEntero(scanner, "Código del producto: ");

            pedidoService.agregarProducto(p.getCodigo(), codigoProducto);

            System.out.println("Producto agregado al pedido correctamente");

        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (IllegalStateException e) {
            System.out.println("Error de estado: " + e.getMessage());
        }
    }

    private void cambiarEstado() {

        int codigoPedido = leerEntero(scanner, "Código del pedido: ");

        System.out.println("Estados disponibles:");
        for (EstadoPedido estado : EstadoPedido.values()) {
            System.out.println("- " + estado);
        }

        String estadoInput = leerTexto(scanner, "Nuevo estado: ").toUpperCase();

        try {
            EstadoPedido estado = EstadoPedido.valueOf(estadoInput);
            pedidoService.cambiarEstado(codigoPedido, estado);

            System.out.println("Estado actualizado correctamente");

        } catch (IllegalArgumentException e) {
            System.out.println("Estado inválido");
        }
    }

    @Override
    protected void modificar() {
        System.out.println("No se implementa modificación directa de pedidos.");
    }

    @Override
    protected void eliminar() {
        int codigo = leerEntero(scanner, "Código del pedido: ");

        try {
            if (leerSiNo(scanner, "¿Eliminar pedido?")) {
                pedidoService.eliminar(codigo);
                System.out.println("Pedido eliminado correctamente");
            } else
                System.out.println("Operación cancelada");

        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}
