package com.ecommerce.service;

import com.ecommerce.domain.repository.Repositorio;
import com.ecommerce.domain.exception.*;
import com.ecommerce.utils.Secuencias;
import com.ecommerce.domain.model.*;
import static com.ecommerce.utils.Validar.esVacio;

import java.util.List;

public class ProductoService {
    private final Repositorio<Producto> repoProductos;
    private final Repositorio<Categoria> repoCategorias;

    public ProductoService(Repositorio<Producto> repoProductos, Repositorio<Categoria> repoCategorias) {
        this.repoProductos = repoProductos;
        this.repoCategorias = repoCategorias;
    }

    public void crearAlimenticio(String nombre, double precio, Categoria categoria, int stock, int vencimiento) {
        validarCategoria(categoria);
        validarDuplicado(nombre);

        Producto p = new ProductoAlimenticio(Secuencias.generarCodigoProducto(), nombre, precio, categoria, stock, vencimiento);
        repoProductos.agregar(p);
    }

    public void crearElectronico(String nombre, double precio, Categoria categoria, int stock, double garantiaMeses) {
        validarCategoria(categoria);
        validarDuplicado(nombre);

        Producto p = new ProductoElectronico(Secuencias.generarCodigoProducto(), nombre, precio, categoria, stock, garantiaMeses);
        repoProductos.agregar(p);
    }

    public List<Producto> listar() {
        return repoProductos.listar();
    }

    public Producto buscarPorCodigo(int codigo) {

        Producto p = repoProductos.buscarPorCodigo(codigo);

        if (p == null)
            throw new ProductoNoEncontradoException(codigo);

        return p;
    }

    public Producto buscarPorNombre(String nombre) {

        for (Producto p : repoProductos.listar()) {
            if (p.getNombre().equalsIgnoreCase(nombre))
                return p;
        }

        return null;
    }

    public void modificar(Producto p, String nombre, int unidades, double precio) {

        p.aumentarStock(unidades);
        p.setPrecio(precio);

        // Si se quiere cambiar el nombre, verifico que el nuevo nombre sea distinto al de los productos existentes
        if (!esVacio(nombre)) {
            Producto existente = buscarPorNombre(nombre);

            if (existente != null)
                throw new ProductoDuplicadoException(nombre);

            p.setNombre(nombre);
        }
    }

    public void eliminar(int codigo) {
        Producto p = buscarPorCodigo(codigo);
        repoProductos.eliminar(p);
    }

    public List<Categoria> listarCategorias() {
        return repoCategorias.listar();
    }

    public Categoria buscarCategoriaPorCodigo(int codigo) {
        Categoria c = repoCategorias.buscarPorCodigo(codigo);

        if (c == null)
            throw new CategoriaNoEncontradaException(codigo);

        return c;
    }

    private void validarDuplicado(String nombre) {

        try {
            if (buscarPorNombre(nombre) != null)
                throw new ProductoDuplicadoException(nombre);
        } catch (ProductoNoEncontradoException e){}

    }

    private void validarCategoria(Categoria categoria) {

        if (repoCategorias.buscarPorCodigo(categoria.getCodigo()) == null)
            throw new CategoriaNoEncontradaException(categoria.getCodigo());
    }

}