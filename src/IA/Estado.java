package IA;

import IA.Azamon.*;
import java.util.ArrayList;
import java.util.Random;

public class Estado {
    private int felicidad, precio;

    private ArrayList<PaqueSet> paquetesOfertados;
    public static Transporte ofertas;
    public static Paquetes paquetes;


    public Estado(ArrayList<PaqueSet> paquetesOfertados, int felicidad, int precio) {
        this.paquetesOfertados = paquetesOfertados;
        this.felicidad = felicidad;
        this.precio = precio;
    }

    private void metePaquete(int paquete, Random random) {
        int transporteRandom = random.nextInt();
        if (paquetesOfertados.get(transporteRandom).getPeso() + Paquetes. ) {

        }
    }

    public Estado(int seed) {
        Random random = new Random(seed);
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
