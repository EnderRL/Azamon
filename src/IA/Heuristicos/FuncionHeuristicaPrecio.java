package IA.Heuristicos;

import IA.Estado;
import aima.search.framework.HeuristicFunction;

public class FuncionHeuristicaPrecio implements HeuristicFunction {
    @Override
    public double getHeuristicValue(Object state) {
        //Este redondeo es necesario para que HillClimbing no se quede iterando en estados cuya diferencia de heuristicos es <= 0,00000001.
        return ((double)Math.round((((Estado)state).getPrecio()*1000))/1000);
    }
}
