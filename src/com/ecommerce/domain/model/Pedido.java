package com.ecommerce.domain.model;

import com.ecommerce.domain.interfaces.Identificable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Identificable {

    private int codigo;
    private List<Producto> productos;
    private EstadoPedido estado;

    public Pedido(int codigo) {
        this.codigo = codigo;
        this.productos = new ArrayList<>();
        this.estado = EstadoPedido.CREADO;
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public void agregarProducto(Producto producto) {
        if (producto == null)
            throw new IllegalArgumentException("Error al agregar: Producto inválido");

        productos.add(producto);
    }

    public double calcularTotal() {
        double total = 0;

        for (Producto producto : productos)
            total += producto.calcularPrecioFinal();

        return total;
    }

    @Override
    public String toString() {
        return "Pedido #" + codigo +
                " | Productos: " + productos.size() +
                " | Estado: " + estado +
                " | Total: $" + calcularTotal();
    }
}
