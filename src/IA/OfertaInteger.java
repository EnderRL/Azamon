package IA;

import IA.Azamon.Oferta;

public class OfertaInteger implements Comparable<OfertaInteger> {
    private int indice;



    private Oferta oferta;

    public OfertaInteger(int indice, Oferta oferta) {
        this.oferta = oferta;
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }
    public Oferta getOferta() {
        return oferta;
    }

    @Override
    public int compareTo(OfertaInteger o) {
        int comparacion = Integer.compare(oferta.getDias(), o.oferta.getDias());
        if(comparacion == 0) return Integer.compare(indice, o.indice);
        return comparacion;
    }
}
