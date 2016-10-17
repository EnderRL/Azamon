package IA;

import aima.search.framework.HeuristicFunction;

public class FuncionHeuristicaFelicidad implements HeuristicFunction {
    @Override
    public double getHeuristicValue(Object state) {
        return -1*((Estado)state).getFelicidad();
    }
}
