package com.ecommerce.domain.exception;

public class CategoriaDuplicadaException extends RuntimeException {

    public CategoriaDuplicadaException(String nombre) {
        super("Ya existe una categoría con el nombre " + nombre);
    }
}
