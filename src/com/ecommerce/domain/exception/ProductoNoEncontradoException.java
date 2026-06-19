package com.ecommerce.domain.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(int codigo) {
        super("No existe un producto con código " + codigo);
    }

    public ProductoNoEncontradoException(String nombre) {
        super("No existe un producto con nombre " + nombre);
    }
}