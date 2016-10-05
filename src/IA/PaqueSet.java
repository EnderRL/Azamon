package IA;
import IA.Azamon.Paquete;

import java.util.Objects;
import java.util.TreeSet;


public class PaqueSet extends TreeSet<PaqueteInteger> {
     private double peso = 0;

    @Override
    public boolean add(PaqueteInteger paquete) {
        if (super.add(paquete)) {
            peso += paquete.getPeso();
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object paquete) {
        if (super.remove(paquete)) {
            peso -= ((PaqueteInteger)paquete).getPeso();
            return true;
        }

        return false;
    }

    public double getPeso() {
        return peso;
    }
}
