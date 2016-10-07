package IA;

import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class GeneradorSucesores  {

    private ArrayList<Estado> sucesores;

    public ArrayList<Estado> getSucesores() {
        return sucesores;
    }

    //OPERADOR MOVER
    private boolean moverPaquete(int indicePaquete, int indiceOferta, int indiceOferta2, ArrayList<Double> pesoOfertas, ArrayList<Integer> asignacionPaquetes) {
        Paquete paquete =  Estado.getPaquetes().get(indicePaquete);
        Oferta oferta = Estado.getOfertas().get(indiceOferta2);

        if (pesoOfertas.get(indiceOferta2) + paquete.getPeso() <= oferta.getPesomax() && Estado.calculaDias(paquete.getPrioridad(),oferta.getDias())) {
            pesoOfertas.set(indiceOferta, pesoOfertas.get(indiceOferta) - paquete.getPeso());
            pesoOfertas.set(indiceOferta2, pesoOfertas.get(indiceOferta2) + paquete.getPeso());
            asignacionPaquetes.set(indicePaquete,indiceOferta2);
            return true;
        }
        return false;
    }

    //OPERADOR INTERCAMBIAR
    private boolean intercambiaPaquete(int indicePaquete1, int indicePaquete2, int indiceOferta1, int  indiceOferta2, ArrayList<Double> pesoOfertas, ArrayList<Integer> asignacionPaquetes) {
        Paquete paquete1 = Estado.getPaquetes().get(indicePaquete1);
        Paquete paquete2 = Estado.getPaquetes().get(indicePaquete2);
        Oferta oferta1 = Estado.getOfertas().get(indiceOferta1);
        Oferta oferta2 = Estado.getOfertas().get(indiceOferta2);

        if (paquete1.getPeso() + pesoOfertas.get(indiceOferta2) - paquete2.getPeso() <= oferta2.getPesomax() && paquete2.getPeso() + pesoOfertas.get(indiceOferta1) - paquete1.getPeso() <= oferta1.getPesomax() &&
                Estado.calculaDias(paquete1.getPrioridad(),oferta1.getDias()) &&  Estado.calculaDias(paquete2.getPrioridad(),oferta2.getDias())) {
            pesoOfertas.set(indiceOferta1, pesoOfertas.get(indiceOferta1) - paquete1.getPeso() + paquete2.getPeso());
            pesoOfertas.set(indiceOferta2, pesoOfertas.get(indiceOferta2) + paquete1.getPeso() - paquete2.getPeso());
            asignacionPaquetes.set(indicePaquete1,indiceOferta2);
            asignacionPaquetes.set(indicePaquete2,indiceOferta1);
            return true;
        }
        return false;
    }

    public GeneradorSucesores(Estado estadopadre) {
        sucesores = new ArrayList<>();
        //OPERADOR MOVER
        for (int i = 0; i < Estado.getPaquetes().size(); ++i) {
            for (int j = 0; j < Estado.getOfertas().size(); ++j) {
                int ofertaPaqueteActual = estadopadre.getAsignacionPaquetes().get(i);
                if (j != ofertaPaqueteActual) {
                    if (moverPaquete(i,ofertaPaqueteActual,j,estadopadre.getPesoOfertas(),estadopadre.getAsignacionPaquetes())) {
                        return;
                    }
                }
            }
        }
    }
}
