package IA.Generadores;

import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Estado;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import aima.util.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GeneradorSucesoresSimulatedAnnealing implements SuccessorFunction {
    private Estado estadoPadre;
    private ArrayList<Double> pesoOfertas;
    private ArrayList<Integer> asignacionPaquetes;
    private int felicidad;
    private double precio;
    private Random random;

    //OPERADOR MOVER
    private boolean moverPaquete(int indicePaquete, int indiceOferta) {
        Paquete paquete = Estado.getPaquetes().get(indicePaquete);
        int indiceOfertaActual = asignacionPaquetes.get(indicePaquete);
        Oferta oferta = Estado.getOfertas().get(indiceOferta);

        if (pesoOfertas.get(indiceOferta) + paquete.getPeso() <= oferta.getPesomax() && Estado.calculaDias(paquete.getPrioridad(), oferta.getDias())) {
            pesoOfertas.set(indiceOfertaActual, pesoOfertas.get(indiceOfertaActual) - paquete.getPeso());
            pesoOfertas.set(indiceOferta, pesoOfertas.get(indiceOferta) + paquete.getPeso());
            asignacionPaquetes.set(indicePaquete, indiceOferta);
            //ACTUALIZA FELICIDAD
            felicidad = estadoPadre.getFelicidad() - Estado.calculaFelicidad(indiceOfertaActual,paquete) + Estado.calculaFelicidad(indiceOferta,paquete);
            //ACTUALIZA PRECIO
            precio = estadoPadre.getPrecio() - Estado.calculaPrecio(indiceOfertaActual,paquete) + Estado.calculaPrecio(indiceOferta,paquete);
            return true;
        }
        return false;
    }

    //OPERADOR INTERCAMBIAR
    private boolean intercambiaPaquete(int indicePaquete1, int indicePaquete2) {
        Paquete paquete1 = Estado.getPaquetes().get(indicePaquete1);
        Paquete paquete2 = Estado.getPaquetes().get(indicePaquete2);
        int indiceOferta1 = asignacionPaquetes.get(indicePaquete1);
        int indiceOferta2 = asignacionPaquetes.get(indicePaquete2);
        Oferta oferta1 = Estado.getOfertas().get(indiceOferta1);
        Oferta oferta2 = Estado.getOfertas().get(indiceOferta2);

        if (paquete1.getPeso() + pesoOfertas.get(indiceOferta2) - paquete2.getPeso() <= oferta2.getPesomax() && paquete2.getPeso() + pesoOfertas.get(indiceOferta1) - paquete1.getPeso() <= oferta1.getPesomax() &&
                Estado.calculaDias(paquete1.getPrioridad(), oferta2.getDias()) && Estado.calculaDias(paquete2.getPrioridad(), oferta1.getDias())) {
            pesoOfertas.set(indiceOferta1, pesoOfertas.get(indiceOferta1) - paquete1.getPeso() + paquete2.getPeso());
            pesoOfertas.set(indiceOferta2, pesoOfertas.get(indiceOferta2) + paquete1.getPeso() - paquete2.getPeso());
            asignacionPaquetes.set(indicePaquete1, indiceOferta2);
            asignacionPaquetes.set(indicePaquete2, indiceOferta1);
            //ACTUALIZA FELICIDAD
            felicidad = estadoPadre.getFelicidad() - Estado.calculaFelicidad(indiceOferta1,paquete1) + Estado.calculaFelicidad(indiceOferta2,paquete1)  - Estado.calculaFelicidad(indiceOferta2,paquete2) + Estado.calculaFelicidad(indiceOferta1,paquete2);
            //ACTUALIZA PRECIO
            precio = estadoPadre.getPrecio() - Estado.calculaPrecio(indiceOferta1,paquete1) + Estado.calculaPrecio(indiceOferta2,paquete1) - Estado.calculaPrecio(indiceOferta2,paquete2) + Estado.calculaPrecio(indiceOferta1,paquete2);
            return true;
        }
        return false;
    }

    public GeneradorSucesoresSimulatedAnnealing(int seed) {
        random = new Random(seed);
    }

    @Override
    public List getSuccessors(Object state) {
        estadoPadre = (Estado)state;
        asignacionPaquetes = (ArrayList<Integer>) estadoPadre.getAsignacionPaquetes().clone();
        pesoOfertas = (ArrayList<Double>) estadoPadre.getPesoOfertas().clone();
        felicidad = estadoPadre.getFelicidad();
        precio = estadoPadre.getPrecio();
        LinkedList<Successor> sucesores = new LinkedList<>();
        boolean success = false;
        Estado nextEstado = null;
        while (!success) {
            if (random.nextInt(2) == 0) {
                //OPERADOR MOVER
                int oferta = random.nextInt(Estado.getOfertas().size());
                int paquete = random.nextInt(Estado.getPaquetes().size());
                if (asignacionPaquetes.get(paquete) != oferta && moverPaquete(paquete, oferta)) {
                    nextEstado = new Estado(asignacionPaquetes,pesoOfertas,felicidad,precio);
                    success = true;
                }
            }
            else {
                //OPERADOR INTERCAMBIAR
                int paquete1 = random.nextInt(Estado.getPaquetes().size());
                int paquete2 = random.nextInt(Estado.getPaquetes().size());
                if (!asignacionPaquetes.get(paquete1).equals(asignacionPaquetes.get(paquete2)) && intercambiaPaquete(paquete1, paquete2)) {
                    nextEstado = new Estado(asignacionPaquetes,pesoOfertas,felicidad,precio);
                    success = true;
                }
            }
        }
        sucesores.add(new Successor("", nextEstado));
        return sucesores;
    }
}


