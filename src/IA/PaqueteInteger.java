package IA;

import IA.Azamon.Paquete;

import java.util.IdentityHashMap;

/**
 * Created by juan.miguel.de.haro on 05/10/2016.
 */
public class PaqueteInteger implements Comparable<PaqueteInteger> {

    private int indice;
    private Paquete paquete;

    public double getPeso() {
        return paquete.getPeso();
    }

    public PaqueteInteger(int indice, Paquete paquete) {
        this.paquete = paquete;
        this.indice = indice;
    }

    @Override
    public int compareTo(PaqueteInteger o) {
        int comparacion = Double.compare(paquete.getPeso(), o.paquete.getPeso());
        if(comparacion == 0) return Integer.compare(indice, o.indice);
        return comparacion;
    }
}
