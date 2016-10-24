package IA.Heuristicos;

import IA.Estado;
import aima.search.framework.HeuristicFunction;

public class FuncionHeuristica implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object n) {
        Estado estado =(Estado) n;
        double heuristico = ((double)Math.round((((Estado)estado).getPrecio()*1000))/1000) - estado.getFelicidad()*10;
        return heuristico;
    }
}
