package com.ecommerce.domain.model;

public class ProductoElectronico extends Producto {

    private double mesesGarantia;

    public ProductoElectronico(int codigo, String nombre, double precio, Categoria categoria, int stock, double mesesGarantia) {
        super(codigo, nombre, precio, categoria, stock);
        this.mesesGarantia = mesesGarantia;
    }

    public double getMesesGarantia() {
        return mesesGarantia;
    }

    @Override
    public double calcularPrecioFinal() {

        if (mesesGarantia > 12)
            return precio * 1.10;

        return precio;
    }

        @Override
    public String getDetalleEspecifico(){
        return "Meses de garantía: " + (int)this.mesesGarantia;
    }
}
