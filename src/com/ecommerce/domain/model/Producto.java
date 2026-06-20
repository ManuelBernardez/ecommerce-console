package com.ecommerce.domain.model;

import com.ecommerce.domain.interfaces.*;

public abstract class Producto implements Calculable, Identificable
{
    protected int codigo;
    protected String nombre;
    protected double precio;
    protected Categoria categoria;
    protected int stock;

    public Producto(int codigo, String nombre, double precio, Categoria categoria, int stock){
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
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

    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double nuevoPrecio){
        this.precio = nuevoPrecio;
    }

    public int getStock() {
        return stock;
    }

    public void aumentarStock(int cantidad) {
        stock += cantidad;
    }

    public boolean descontarStock(int cantidad) {
        if (cantidad > stock)
            return false;

        stock -= cantidad;
        return true;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public abstract double calcularPrecioFinal();

    public abstract String getDetalleEspecifico();

    @Override
    public String toString() {
        return String.format(
                "ID [%d] | Nombre: %s | Precio: $%.2f | Categoría: %s | Stock: %s | %s",
                codigo, nombre, calcularPrecioFinal(), categoria.getNombre(), stock, getDetalleEspecifico()
        );
    }
}

