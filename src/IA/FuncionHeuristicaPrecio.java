package IA;

import aima.search.framework.HeuristicFunction;

public class FuncionHeuristicaPrecio implements HeuristicFunction {
    @Override
    public double getHeuristicValue(Object state) {
        return ((Estado)state).getPrecio();
    }
}
