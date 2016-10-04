package IA;

import IA.Azamon.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Estado {
    private double felicidad, precio;

    private ArrayList<PaqueSet> paquetesOfertados;

    private static Transporte ofertas;
    private static Paquetes paquetes;


    public Estado(ArrayList<PaqueSet> paquetesOfertados, int felicidad, int precio) {
        this.paquetesOfertados = paquetesOfertados;
        this.felicidad = felicidad;
        this.precio = precio;
    }

    private boolean metePaquete(int paquete, Random random) {
        if (paquete == paquetes.size()) return true;
        System.out.println("Hola soy la funcion metePaquete y estoy metiendo el paquete " + paquete);
        ArrayList<Integer> conjuntoOfertas = new ArrayList<>(ofertas.size());
        for (int i = 0; i < ofertas.size(); ++i) conjuntoOfertas.add(i);
        while (conjuntoOfertas.size() > 0) {
            int transporteRandom = random.nextInt(conjuntoOfertas.size());
            Paquete paqueteRandom = paquetes.get(paquete);
            Oferta ofertaRandom = ofertas.get(conjuntoOfertas.get(transporteRandom));
            PaqueSet paquetesOferta = paquetesOfertados.get(conjuntoOfertas.get(transporteRandom));
            if (paquetesOferta.getPeso() + paqueteRandom.getPeso() <= ofertaRandom.getPesomax()){
                paquetesOferta.put(paquete,paqueteRandom);
                if(!metePaquete(paquete + 1,random)){
                    paquetesOferta.remove(paquete);
                    conjuntoOfertas.remove(transporteRandom);
                }
                else return true;
            }
        }
        return false;
    }

    public Estado(int seed) {
        paquetesOfertados = new ArrayList<>(ofertas.size());
        for (int i = 0; i < ofertas.size(); ++i) {
            paquetesOfertados.add(new PaqueSet());
        }
        Random random = new Random(seed);
        System.out.println("El numero de paquetes es " + paquetes.size() + " i el numero de ofertas es " + ofertas.size());
        metePaquete(0, random);
    }

    public double getFelicidad() {
        return felicidad;
    }

    public double getPrecio() {
        return precio;
    }

    public ArrayList<PaqueSet> getPaquetesOfertados() {
        return paquetesOfertados;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Número de ofertas de transporte: " + paquetesOfertados.size() + "\n";
        for (int i = 0; i < paquetesOfertados.size(); ++i) {
            s += "Oferta número " + i + " con peso " + paquetesOfertados.get(i).getPeso() + "/" + ofertas.get(i).getPesomax() + ":\n";
            for (Map.Entry<Integer,Paquete> p : paquetesOfertados.get(i).entrySet()) {
                s += "\t" +  p.getValue() + "\n";
            }
        }
        return s;
    }

    public static void setOfertas(Transporte ofertas) {
        Estado.ofertas = ofertas;
    }

    public static void setPaquetes(Paquetes paquetes) {
        Estado.paquetes = paquetes;
    }

}
