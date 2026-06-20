package com.ecommerce.presentation.console;

import static com.ecommerce.utils.EntradaDatos.*;

import com.ecommerce.domain.exception.CategoriaNoEncontradaException;
import com.ecommerce.domain.exception.ProductoDuplicadoException;
import com.ecommerce.domain.exception.ProductoNoEncontradoException;
import com.ecommerce.domain.model.Categoria;
import com.ecommerce.service.ProductoService;
import com.ecommerce.domain.model.Producto;
import com.ecommerce.utils.EntradaDatos;

import java.util.Scanner;

public class MenuProductos extends Menu {

    private final ProductoService productoService;

    public MenuProductos(Scanner scanner, ProductoService productoService) {
        super(scanner);
        this.productoService = productoService;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ PRODUCTOS ---");
        System.out.println("1. Ingresar producto");
        System.out.println("2. Listar producto");
        System.out.println("3. Consultar producto");
        System.out.println("4. Modificar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Volver");
        System.out.println("----------------------");
    }

    @Override
    public void ejecutar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "\nElija una opción(1-6): ");

            switch (opcion) {
                case 1:
                    System.out.println("\n--- NUEVO PRODUCTO ---");
                    crear();

                    break;
                case 2:
                    System.out.println("\n--- LISTADO DE PRODUCTOS ---");
                    listar();

                    break;
                case 3:
                    System.out.println("\n--- CONSULTAR PRODUCTO ---");
                    int tipoBusqueda = leerEntero(scanner, "1: Buscar por código / 2: Buscar por nombre: ");

                    switch (tipoBusqueda) {
                        case 1:
                            buscarPorCodigo();
                            break;

                        case 2:
                            buscarPorNombre();
                            break;

                        default:
                            System.out.println("Opción inválida");
                    }

                    break;
                case 4:
                    System.out.println("\n--- MODIFICAR PRODUCTOS ---");
                    listar();
                    modificar();

                    break;
                case 5:
                    System.out.println("\n--- ELIMINAR PRODUCTOS ---");
                    eliminar();

                    break;
                case 6:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Error: Opción inválida.");
            }

        } while (opcion != 6);
    }

    @Override
    protected void crear() {

        try {
            String nombre = leerTexto(scanner, "Ingrese nombre: ");
            double precio = leerDouble(scanner, "Ingrese precio: ");

            System.out.println("\nCATEGORÍAS DISPONIBLES");
            for (Categoria c : productoService.listarCategorias()) {
                System.out.println(c);
            }

            int codigoCategoria = leerEntero(scanner, "Ingrese código de categoría: ");
            Categoria categoria = productoService.buscarCategoriaPorCodigo(codigoCategoria);
            int stock = leerEntero(scanner, "Ingrese cantidad en stock: ");

            switch (codigoCategoria) {

                case 1:
                    int vencimiento = leerEntero(scanner, "Vence en (días): ");
                    productoService.crearAlimenticio(nombre, precio, categoria, stock, vencimiento);
                    break;

                case 2:
                    double garantia = leerDouble(scanner, "Meses de garantía: ");
                    productoService.crearElectronico(nombre, precio, categoria, stock, garantia);
                    break;

                default:
                    System.out.println("Tipo de categoría no soportado por lógica de producto");
            }

            System.out.println("Producto creado correctamente");

        } catch (ProductoDuplicadoException | CategoriaNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void listar() {

        // Añadí mensaje de validación
        if (productoService.listar().isEmpty())
            System.out.println("No hay elementos cargados");
        else
            for (Producto p : productoService.listar())
                System.out.println(p);
    }

    @Override
    protected void buscarPorCodigo() {

        try {
            int codigo = leerEntero(scanner, "Ingrese el código: ");

            Producto p = productoService.buscarPorCodigo(codigo);
            System.out.println("Producto: " + p);

        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void buscarPorNombre() {

        try {
            String nombre = leerTexto(scanner, "Ingrese el nombre: ");

            Producto p = productoService.buscarPorNombre(nombre);
            System.out.println("Producto: " + p);

        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void modificar() {

        int codigo = leerEntero(scanner, "Código del producto: ");
        int unidades = 0;
        double precio = 0;
        String nombre = "";

        try{
            Producto producto = productoService.buscarPorCodigo(codigo);

            if(leerSiNo(scanner, "¿Reponer stock?"))
                unidades = EntradaDatos.leerEntero(scanner, "Cantidad de unidades: ");

            if(leerSiNo(scanner, "¿Modificar precio?"))
                precio = EntradaDatos.leerDouble(scanner, "Nuevo precio: ");

            if(leerSiNo(scanner, "¿Modificar nombre?"))
                nombre = leerTexto(scanner, "Nuevo nombre: ");

            productoService.modificar(producto, nombre, unidades, precio);
            System.out.println("Producto modificado correctamente");

        } catch (ProductoDuplicadoException e){
            System.out.println("Error al cambiar de nombre. " + e.getMessage());
        }
    }

    @Override
    protected void eliminar() {

        int codigo = leerEntero(scanner, "Ingrese el código: ");

        try{
            Producto p = productoService.buscarPorCodigo(codigo);
            System.out.println(p);

            if (leerSiNo(scanner, "¿Borrar definitivamente?")) {
                productoService.eliminar(codigo);
                System.out.println("Producto eliminado correctamente");
            }
            else {
                System.out.println("Operación cancelada");
            }

        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
