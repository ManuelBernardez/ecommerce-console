package com.ecommerce.service;

import com.ecommerce.domain.repository.Repositorio;
import com.ecommerce.domain.model.Categoria;
import com.ecommerce.domain.exception.*;
import com.ecommerce.utils.Secuencias;

import java.util.List;

import static com.ecommerce.utils.Validar.esVacio;

public class CategoriaService {

    private final Repositorio<Categoria> repoCategorias;

    public CategoriaService(Repositorio<Categoria> repoCategorias) {
        this.repoCategorias = repoCategorias;
    }

    public void crear(String nombre, String descripcion) {

        if (buscarPorNombre(nombre) != null)
            throw new CategoriaDuplicadaException(nombre);

        Categoria categoria = new Categoria(Secuencias.generarCodigoCategoria(), nombre, descripcion);

        repoCategorias.agregar(categoria);
    }

    public List<Categoria> listar() {
        return repoCategorias.listado();
    }

    public Categoria buscarPorCodigo(int codigo) {

        Categoria c = repoCategorias.buscarPorCodigo(codigo);

        if (c == null)
            throw new CategoriaNoEncontradaException(codigo);

        return c;
    }

    public Categoria buscarPorNombre(String nombre) {

        for (Categoria p : repoCategorias.listado()) {
            if (p.getNombre().equalsIgnoreCase(nombre))
                return p;
        }

        return null;
    }

    public void modificar(Categoria categoria, String nombre, String descripcion) {

        categoria.setDescripcion(descripcion);

        // Si se quiere cambiar el nombre, verifico que el nuevo nombre sea distinto al de los productos existentes
        if (!esVacio(nombre)) {
            Categoria existente = buscarPorNombre(nombre);

            if (existente != null)
                throw new CategoriaDuplicadaException(nombre);

            categoria.setNombre(nombre);
        }
    }

    public void eliminar(int codigo) {
        Categoria c = buscarPorCodigo(codigo);
        repoCategorias.eliminar(c);
    }

    public boolean estaVacio(){
        return repoCategorias.estaVacio();
    }
}
