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
    private boolean moverPaquete(int indicePaquete, Oferta oferta, PaqueMap paquetesOfertaActual, PaqueMap paquetesOfertaDestino) {
        Paquete paquete = Estado.getPaquetes().get(indicePaquete);
        System.out.println("Estoy en la funcion mover i muevo el paquete  " + paquete + " con indice " + indicePaquete);
        if (paquete.getPeso() + paquetesOfertaDestino.getPeso() <= oferta.getPesomax() && Estado.calculaDias(paquete.getPrioridad()+1,oferta.getDias())) {
            paquetesOfertaActual.remove(indicePaquete);
            paquetesOfertaDestino.put(indicePaquete,paquete);
            return true;
        }
        return false;
    }

    //OPERADOR INTERCAMBIAR
    private boolean intercambiaPaquete(int indicePaquete1, int indicePaquete2, Oferta oferta1, Oferta oferta2, PaqueMap paquetesOferta1, PaqueMap paquetesOferta2) {
        Paquete paquete1 = Estado.getPaquetes().get(indicePaquete1);
        Paquete paquete2 = Estado.getPaquetes().get(indicePaquete2);
        if (paquete1.getPeso() + paquetesOferta2.getPeso() - paquete2.getPeso() <= oferta2.getPesomax() && paquete2.getPeso() + paquetesOferta1.getPeso() - paquete1.getPeso() <= oferta1.getPesomax() &&
                Estado.calculaDias(paquete1.getPrioridad()+1,oferta1.getDias()) &&  Estado.calculaDias(paquete2.getPrioridad(),oferta2.getDias())) {
            paquetesOferta1.remove(paquete1);
            paquetesOferta2.put(indicePaquete1,paquete1);
            paquetesOferta2.remove(paquete2);
            paquetesOferta1.put(indicePaquete2,paquete2);
            return true;
        }
        return false;
    }

    public GeneradorSucesores(Estado estadopadre) {
        sucesores = new ArrayList<>();
        //OPERADOR MOVER
        for (int i = 0; i < Estado.getOfertas().size(); ++i) {
            for (Map.Entry<Integer,Paquete> paqueteAMover : estadopadre.getPaquetesOfertados().get(i).entrySet()) {
                for (int j = 0; j < Estado.getOfertas().size(); ++j) {
                    System.out.println("El paquete a mover es " + paqueteAMover.getValue());
                    System.out.print("Ahora estoy probando de mover el paquete numero " + paqueteAMover.getKey() + " que esta en la oferta " + i + " a la oferta " + j + "\n");
                    if (i != j && moverPaquete(paqueteAMover.getKey(),Estado.getOfertas().get(j),estadopadre.getPaquetesOfertados().get(i),estadopadre.getPaquetesOfertados().get(j))) {
                        System.out.println("He acertado.");
                        System.out.println(estadopadre);
                        return;
                    }
                    else System.out.println("He faileado");
                }
            }
        }


    }



}
