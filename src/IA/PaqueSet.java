package IA;

import IA.Azamon.Paquete;

import java.util.TreeMap;

public class PaqueSet extends TreeMap<Integer,Paquete> {
     private double peso = 0;

    @Override
    public Paquete put(Integer i, Paquete paquete) {
        Paquete paquete1 = super.put(i, paquete);
        peso += paquete.getPeso();
        if (paquete1 != null) peso -= paquete1.getPeso();
        return paquete1;
    }

    @Override
    public Paquete remove(Object paquete) {
        Paquete paquete1 = super.remove(paquete);
        if(paquete1 != null) {
            peso -= paquete1.getPeso();
        }
        return paquete1;
    }

    public double getPeso() {
        return peso;
    }
}
