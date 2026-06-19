package com.ecommerce.service;

import com.ecommerce.domain.model.*;
import com.ecommerce.domain.repository.Repositorio;
import com.ecommerce.domain.exception.*;

import com.ecommerce.utils.Secuencias;

import java.util.List;

public class PedidoService {

    private final Repositorio<Pedido> repoPedidos;
    private final Repositorio<Producto> repoProductos;

    public PedidoService(Repositorio<Pedido> repoPedidos, Repositorio<Producto> repoProductos) {
        this.repoPedidos = repoPedidos;
        this.repoProductos = repoProductos;
    }

    public Pedido crearPedido() {
        Pedido pedido = new Pedido(Secuencias.generarCodigoPedido());
        repoPedidos.agregar(pedido);
        return pedido;
    }

    public List<Pedido> listar() {
        return repoPedidos.listado();
    }

    public Pedido buscarPorCodigo(int codigo) {
        Pedido p = repoPedidos.buscarPorCodigo(codigo);

        if (p == null)
            throw new RuntimeException("Pedido no encontrado: " + codigo);

        return p;
    }

    public void agregarProducto(int codigoPedido, int codigoProducto) {

        Pedido pedido = buscarPorCodigo(codigoPedido);
        Producto producto = repoProductos.buscarPorCodigo(codigoProducto);

        if (producto == null)
            throw new ProductoNoEncontradoException(codigoProducto);

        if (pedido.getEstado() != EstadoPedido.CREADO)
            throw new IllegalStateException("Sólo se pueden modificar pedidos si están en estado 'CREADO'");

        pedido.agregarProducto(producto);
    }

    public void cambiarEstado(int codigoPedido, EstadoPedido estado) {
        Pedido pedido = buscarPorCodigo(codigoPedido);
        pedido.setEstado(estado);
    }

    public void eliminar(int codigo) {
        Pedido pedido = buscarPorCodigo(codigo);
        repoPedidos.eliminar(pedido);
    }

    public void listar_productos(){
        for (Producto p : repoProductos.listado())
            System.out.println(p);
    }
}