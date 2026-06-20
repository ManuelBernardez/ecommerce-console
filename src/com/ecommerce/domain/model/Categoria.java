package com.ecommerce.domain.model;

import com.ecommerce.domain.interfaces.Identificable;

public class Categoria implements Identificable {

    private int codigo;
    private String nombre;
    private String descripcion;

    public Categoria(int codigo, String nombre, String descripcion){
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nuevoNombre){
        this.nombre = nuevoNombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String nuevaDescripcion){
        this.descripcion = nuevaDescripcion;
    }

    @Override
    public String toString() {
        return "ID [" + codigo + "] - Nombre: " + nombre + " - Descripción: " + getDescripcion();
    }
}

