package com.ecommerce.utils;

public final class Secuencias {

    private static int contadorProductos = 0;
    private static int contadorCategorias = 0;
    private static int contadorPedidos = 0;

    private Secuencias() {
    }

    public static int generarCodigoProducto() {
        return ++contadorProductos;
    }

    public static int generarCodigoCategoria() {
        return ++contadorCategorias;
    }

    public static int generarCodigoPedido(){
        return ++contadorPedidos;
    }
}
