package com.ecommerce.domain.repository;
import java.util.ArrayList;
import com.ecommerce.domain.interfaces.Identificable;

public class Repositorio<T extends Identificable> {

    private final ArrayList<T> lista;

    public Repositorio() {
        this.lista = new ArrayList<>();
    }

    public void agregar (T objeto) {

        if (objeto == null)
            throw new IllegalArgumentException("Debe agregar un elemento válido");

        if (buscarPorCodigo(objeto.getCodigo()) != null)
            throw new IllegalArgumentException("Ya existe un elemento con ese código");

        lista.add(objeto);
    }

    public ArrayList<T> listar() {
        return new ArrayList<>(lista);
    }

    public T buscarPorCodigo(int codigo){

        for(T objeto : lista){
            if(objeto.getCodigo() == codigo)
                return objeto;
        }

        return null;
    }

    public boolean eliminar(T objeto) {

        if (objeto != null){
            lista.remove(objeto);
            return true;
        }

        return false;
    }

    public boolean estaVacio() {
        return lista.isEmpty();
    }
}
