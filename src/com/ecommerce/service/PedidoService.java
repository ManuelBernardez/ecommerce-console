package com.ecommerce.service;

import com.ecommerce.domain.model.*;
import com.ecommerce.domain.repository.Repositorio;

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
        return repoPedidos.listar();
    }

    public Pedido buscarPorCodigo(int codigo) {
        Pedido p = repoPedidos.buscarPorCodigo(codigo);

        if (p == null)
            throw new RuntimeException("No existe un pedido de código " + codigo);

        return p;
    }

    public void agregarProducto(int codigoPedido, int codigoProducto, int cantidad) {

        Pedido pedido = buscarPorCodigo(codigoPedido);
        Producto producto = repoProductos.buscarPorCodigo(codigoProducto);

        if (!producto.descontarStock(cantidad)) {
            System.out.println("Stock insuficiente, quedan " + producto.getStock() + " unidades");
            return;
        }

        pedido.agregarProducto(producto, cantidad);
        System.out.println("Producto agregado correctamente");
    }

    public void quitarProducto(int codigoPedido, int codigoProducto, int cantidad) {

        Pedido pedido = buscarPorCodigo(codigoPedido);
        Producto producto = repoProductos.buscarPorCodigo(codigoProducto);

        int removidos = pedido.quitarProducto(producto, cantidad);

        if (removidos == 0)
            System.out.println("El producto no está en el pedido");
        else{

            producto.aumentarStock(removidos);
            System.out.println("Se eliminaron " + removidos + " unidades del pedido");
        }

    }

    public boolean cambiarEstado(int codigoPedido, EstadoPedido estado) {
        Pedido pedido = buscarPorCodigo(codigoPedido);

        if(pedido.getEstado() == EstadoPedido.ENVIADO && estado == EstadoPedido.CANCELADO){
            System.out.println("No es posible cancelar un pedido enviado");
            return false;
        }

        pedido.setEstado(estado);
        return true;
    }

    public boolean eliminar(int codigo) {
        Pedido pedido = buscarPorCodigo(codigo);

        if(pedido.getEstado() == EstadoPedido.ENVIADO){
            System.out.println("No es posible eliminar un pedido enviado");
            return false;
        }

        repoPedidos.eliminar(pedido);
        return true;
    }

    public boolean mostrarProductos(){
        if(repoProductos.estaVacio()){
            System.out.println("No hay productos cargados");
            return false;
        }

        System.out.println("ID | Nombre | Precio | Stock");

        for (Producto p : repoProductos.listar())
            System.out.println(p.getCodigo() + " | " + p.getNombre() + " | $" + p.getPrecio() + " | " + p.getStock() );

        System.out.println("------------------------------");

        return true;
    }

}