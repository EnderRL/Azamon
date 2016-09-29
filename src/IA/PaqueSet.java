package IA;

import IA.Azamon.Paquete;

import java.util.TreeSet;

public class PaqueSet extends TreeSet<Paquete> {
     private int peso;

    @Override
    public boolean add(Paquete paquete) {
        peso += paquete.getPeso();
        return super.add(paquete);
    }

    @Override
    public boolean remove(Object paquete) {
        if(super.remove(paquete)){
            peso -= ((Paquete)paquete).getPeso();
            return true;
        }
        return false;
    }

    public int getPeso() {
        return peso;
    }
}
