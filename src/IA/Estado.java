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

    private boolean metePaquete(int paquete, Random random) {
        if (paquete == paquetes.size()) return true;
        int transporteRandom = random.nextInt();
        Paquete paqueteRandom = paquetes.get(paquete);
        Oferta ofertaRandom = ofertas.get(transporteRandom);
        PaqueSet paquetesOferta = paquetesOfertados.get(transporteRandom);
        if (paquetesOferta.getPeso() + paqueteRandom.getPeso() <= ofertaRandom.getPesomax()) {
            paquetesOferta.add(paqueteRandom);
            if (!metePaquete(paquete+1, random)) paquetesOferta.remove(paqueteRandom);
        }
        else return false;
        return true;
    }

    public Estado(int seed) {
        Random random = new Random(seed);
        int i = 0;
        metePaquete(i, random);
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
