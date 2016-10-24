package IA.Generadores;

import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Estado;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GeneradorSucesoresMover implements SuccessorFunction {
    private Estado estadoPadre;
    private ArrayList<Double> pesoOfertas;
    private ArrayList<Integer> asignacionPaquetes;
    private int felicidad;
    private double precio;

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
        asignacionPaquetes = estadoPadre.getAsignacionPaquetes();
        pesoOfertas = estadoPadre.getPesoOfertas();
        felicidad = estadoPadre.getFelicidad();
        precio = estadoPadre.getPrecio();
        LinkedList<Successor> sucesores = new LinkedList<>();
        for (int i = 0; i < Estado.getPaquetes().size(); ++i) {
            int ofertaPaqueteActual = asignacionPaquetes.get(i);
            for (int j = 0; j < Estado.getOfertas().size(); ++j) {
                if (j != ofertaPaqueteActual) {
                    if (moverPaquete(i, j)) {
                        sucesores.add(new Successor("", new Estado((ArrayList<Integer>)asignacionPaquetes.clone(),(ArrayList< Double>)pesoOfertas.clone(),felicidad,precio)));
                        moverPaquete(i,ofertaPaqueteActual);
                    }
                }
            }
        }
        return sucesores;
    }
}
