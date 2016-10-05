package IA;

import IA.Azamon.Paquete;

public class PaqueteInteger implements Comparable<PaqueteInteger> {

    private int indice;

    public Paquete getPaquete() {
        return paquete;
    }

    private Paquete paquete;

    public double getPeso() {
        return paquete.getPeso();
    }

    public PaqueteInteger(int indice, Paquete paquete) {
        this.paquete = paquete;
        this.indice = indice;
    }

    public int getPrioridad() {return paquete.getPrioridad();}

    public int getIndice() {
        return indice;
    }

    @Override
    public int compareTo(PaqueteInteger o) {
        //int comparacion = Double.compare(paquete.getPeso(), o.paquete.getPeso());
        int comparacion = Integer.compare(paquete.getPrioridad(), o.paquete.getPrioridad());
        if(comparacion == 0) return Integer.compare(indice, o.indice);
        return comparacion;
    }
}
