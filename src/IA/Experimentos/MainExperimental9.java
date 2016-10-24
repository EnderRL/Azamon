package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Generadores.GeneradorSucesoresHillClimbing;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class MainExperimental9 {
    public static void main(String[] argv) {
        Paquetes paquetes = new Paquetes(100, 1234);
        Transporte ofertas = new Transporte(paquetes, 1.2, 1234);
        Estado.setOfertas(ofertas);
        Estado.setPaquetes(paquetes);
        Estado estadoInicial = new Estado(1234);
        Problem problem = new Problem(estadoInicial, new GeneradorSucesoresHillClimbing(), state-> true, new FuncionHeuristicaPrecio());
        HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
        try {
            long time = System.nanoTime();
            SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
            time = System.nanoTime()-time;
            Estado estadoFinal = (Estado) hillClimbingSearch.getGoalState();
            System.out.println("Precio: " + estadoFinal.getPrecio() + ", tiempo: " + Math.round(time/1000000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}