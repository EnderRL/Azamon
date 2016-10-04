package IA;

import IA.Azamon.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Estado {
    private int felicidad, precio;

    private ArrayList<PaqueSet> paquetesOfertados;

    public static void setOfertas(Transporte ofertas) {
        Estado.ofertas = ofertas;
    }

    public static void setPaquetes(Paquetes paquetes) {
        Estado.paquetes = paquetes;
    }

    public static Transporte ofertas;
    public static Paquetes paquetes;


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
        System.out.println(conjuntoOfertas.size());
        while (conjuntoOfertas.size() > 0) {
            int transporteRandom = random.nextInt()%conjuntoOfertas.size();
            Paquete paqueteRandom = paquetes.get(paquete);
            Oferta ofertaRandom = ofertas.get(transporteRandom);
            PaqueSet paquetesOferta = paquetesOfertados.get(transporteRandom);
            System.out.println(paqueteRandom + "\n" + ofertaRandom);
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

    public int getFelicidad() {
        return felicidad;
    }

    public int getPrecio() {
        return precio;
    }

    public ArrayList<PaqueSet> getPaquetesOfertados() {
        return paquetesOfertados;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Numero de paquetes: " + paquetesOfertados.size() + "\n";
        for (int i = 0; i < paquetesOfertados.size(); ++i) {
            s += "Oferta numero " + i + "con peso maximo " + paquetesOfertados.get(i).getPeso() + "/" + ofertas.get(i).getPesomax() + ":\n";
            for (Map.Entry<Integer,Paquete> p : paquetesOfertados.get(i).entrySet()) {
                s += "\t" +  p.getValue().toString() + "\n";
            }
        }
        return s;
    }

}
