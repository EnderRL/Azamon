package IA;
import IA.Azamon.Paquete;

import java.util.Objects;
import java.util.TreeMap;

public class PaqueMap extends TreeMap<Integer, Paquete> {
    private double peso = 0;

    @Override
    public Paquete put(Integer index, Paquete paquete) {
        Paquete paquete1 = super.put(index,paquete);
        peso += paquete.getPeso();
        if (paquete1 != null) {
            peso -= paquete1.getPeso();
            return paquete1;
        }
        return null;
    }

    @Override
    public Paquete remove(Object paquete) {
        Paquete paquete1 = super.remove(paquete);
        if (paquete1 != null) {
            peso -= paquete1.getPeso();
            return paquete1;
        }
        return null;
    }

    public double getPeso() {
        return peso;
    }
}
