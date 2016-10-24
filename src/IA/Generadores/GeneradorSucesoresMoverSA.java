package IA.Generadores;

import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Estado;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GeneradorSucesoresMoverSA implements SuccessorFunction {
    private Estado estadoPadre;
    private ArrayList<Double> pesoOfertas;
    private ArrayList<Integer> asignacionPaquetes;
    private Random random;
    private int felicidad;
    private double precio;

    public GeneradorSucesoresMoverSA(int seed) {
        random = new Random(seed);
    }

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

    @Override
    public List getSuccessors(Object state) {
        estadoPadre = (Estado)state;
        asignacionPaquetes = (ArrayList<Integer>) estadoPadre.getAsignacionPaquetes().clone();
        pesoOfertas = (ArrayList<Double>) estadoPadre.getPesoOfertas().clone();
        felicidad = estadoPadre.getFelicidad();
        precio = estadoPadre.getPrecio();
        LinkedList<Successor> sucesores = new LinkedList<>();
        boolean success = false;
        while (!success) {
            int oferta = random.nextInt(Estado.getOfertas().size());
            int paquete = random.nextInt(Estado.getPaquetes().size());
            if (asignacionPaquetes.get(paquete) != oferta && moverPaquete(paquete, oferta)) {
                Estado nextEstado = new Estado(asignacionPaquetes, pesoOfertas, felicidad, precio);
                sucesores.add(new Successor("", nextEstado));
                success = true;
            }
        }
        return sucesores;
    }
}
