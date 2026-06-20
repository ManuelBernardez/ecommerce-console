package com.ecommerce.domain.model;

public class ItemPedido {

    private Producto producto;
    private int cantidad;

    public ItemPedido(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0)
            throw new IllegalArgumentException("Item inválido");

        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void aumentarCantidad(int cantidad) {
        if (cantidad <= 0)
            throw new IllegalArgumentException("Cantidad inválida");

        this.cantidad += cantidad;
    }

    public void disminuirCantidad(int cantidad) {
        if (cantidad <= 0 || cantidad > this.cantidad)
            throw new IllegalArgumentException("Cantidad inválida");

        this.cantidad -= cantidad;
    }

    public double subtotal() {
        return producto.calcularPrecioFinal() * cantidad;
    }

    @Override
    public String toString() {
        return "[" + producto.getCodigo() + "] " + producto.getNombre() +
                " | Cantidad: " + cantidad +
                " | Unitario: $" + producto.calcularPrecioFinal() +
                " | Subtotal: $" + subtotal();
    }
}
