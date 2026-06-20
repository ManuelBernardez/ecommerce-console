package com.ecommerce.domain.model;

public class ProductoAlimenticio extends Producto {

    private int diasParaVencimiento;

    public ProductoAlimenticio(int codigo, String nombre, double precio, Categoria categoria, int stock, int diasParaVencimiento){
        super(codigo, nombre, precio, categoria, stock);
        this.diasParaVencimiento = diasParaVencimiento;
    }

    public int getDiasParaVencimiento() {
        return diasParaVencimiento;
    }

    @Override
    public double calcularPrecioFinal() {

        if (diasParaVencimiento < 15)
            return precio * 0.8;

        return precio;
    }

    @Override
    public String getDetalleEspecifico(){
        return "Días para vencimiento: " + getDiasParaVencimiento();
    }
}
