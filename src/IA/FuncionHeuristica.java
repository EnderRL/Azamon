package IA;

import aima.search.framework.HeuristicFunction;

public class FuncionHeuristica implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object n) {
        Estado estado =(Estado) n;
        double heuristico = estado.getPrecio() - estado.getFelicidad()*10;
        return heuristico;
    }
}
