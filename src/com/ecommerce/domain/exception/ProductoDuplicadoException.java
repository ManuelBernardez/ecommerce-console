package com.ecommerce.domain.exception;

public class ProductoDuplicadoException extends RuntimeException {
    public ProductoDuplicadoException(String nombre) {
        super("Ya existe un producto con el nombre " + nombre);
    }
}
