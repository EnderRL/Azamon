package IA;

import IA.Azamon.*;
import java.util.ArrayList;

public class Estado {
    private int felicidad, precio;

    private ArrayList<PaqueSet> paquetesOfertados;
    public static Transporte ofertas;

    public Estado(ArrayList<PaqueSet> paquetesOfertados, int felicidad, int precio) {
        this.paquetesOfertados = paquetesOfertados;
        this.felicidad = felicidad;
        this.precio = precio;
    }

    public int getFelicidad() {
        return felicidad;
    }

    public int getPrecio() {
        return precio;
    }

    public ArrayList<PaqueSet> getPaquetesOfertados() {
        return paquetesOfertados;
    }
}
