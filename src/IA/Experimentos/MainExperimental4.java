package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresMover;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Utils.Parametros;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class MainExperimental4 {
    public static void main(String[] argv) {
        double proporcion = 1.2;
        for (int i = 0; i < 25; ++i) {
            Paquetes paquetes = new Paquetes(100, Parametros.seed);
            Transporte ofertas = new Transporte(paquetes, proporcion, Parametros.seed);
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            Estado estadoInicial = new Estado(Parametros.seed);
            Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMover(), state -> true, new FuncionHeuristicaPrecio());
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            try {
                long time = System.currentTimeMillis();
                SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
                long time2 = System.currentTimeMillis();
                time = time2-time;
                System.out.println(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
