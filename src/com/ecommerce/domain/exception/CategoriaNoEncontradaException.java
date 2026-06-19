package com.ecommerce.domain.exception;

public class CategoriaNoEncontradaException extends RuntimeException {
    public CategoriaNoEncontradaException(int codigo) {
        super("No existe la categoría con código: " + codigo);
    }

    public CategoriaNoEncontradaException(String nombre) {
        super("No existe una categoría con nombre " + nombre);
    }
}
