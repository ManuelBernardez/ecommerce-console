package com.ecommerce.domain.model;

import com.ecommerce.domain.interfaces.Identificable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Identificable {

    private int codigo;
    private List<ItemPedido> items;
    private EstadoPedido estado;

    public Pedido(int codigo) {
        this.codigo = codigo;
        this.items = new ArrayList<>();
        this.estado = EstadoPedido.CREADO;
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    public List<ItemPedido> getItems() {
        return new ArrayList<>(items);
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public void agregarProducto(Producto producto, int cantidad) {

        if (producto == null)
            throw new IllegalArgumentException("Producto inválido");

        if (estado != EstadoPedido.CREADO)
            throw new IllegalStateException("Sólo se pueden modificar pedidos si están en estado 'CREADO'");

        for (ItemPedido item : items) {
            if (item.getProducto().equals(producto)) {
                item.aumentarCantidad(cantidad);
                return;
            }
        }

        items.add(new ItemPedido(producto, cantidad));
    }

    public int quitarProducto(Producto producto, int cantidad) {

        if (estado != EstadoPedido.CREADO)
            throw new IllegalStateException("El pedido no se puede modificar");

        for (ItemPedido item : items) {
            if (item.getProducto().equals(producto)) {

                int removidos = Math.min(cantidad, item.getCantidad());

                item.disminuirCantidad(removidos);

                if (item.getCantidad() == 0)
                    items.remove(item);

                return removidos;
            }
        }

        return 0;
    }

    public double calcularTotal() {
        double total = 0;

        for (ItemPedido item : items)
            total += item.subtotal();

        return total;
    }

    @Override
    public String toString() {

        String texto = "Pedido #" + codigo +
                " | Estado: " + estado +
                "\n-----------------------------\n";

        for (ItemPedido item : items) {
            texto += item.toString() + "\n";
        }

        texto += "-----------------------------\n";
        texto += "TOTAL: $" + calcularTotal();

        return texto;
    }
}