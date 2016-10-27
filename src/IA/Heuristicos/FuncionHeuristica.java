package IA.Heuristicos;

import IA.Estado;
import aima.search.framework.HeuristicFunction;

public class FuncionHeuristica implements HeuristicFunction {

    private int ponderacionFelicidad;

    public FuncionHeuristica(){
        ponderacionFelicidad = 10;
    }

    public FuncionHeuristica(int ponderacionFelicidad){
        this.ponderacionFelicidad = ponderacionFelicidad;
    }

    @Override
    public double getHeuristicValue(Object n) {
        Estado estado =(Estado) n;
        double heuristico = ((double)Math.round((((Estado)estado).getPrecio()*1000))/1000) - estado.getFelicidad()*ponderacionFelicidad;
        return heuristico;
    }

}
