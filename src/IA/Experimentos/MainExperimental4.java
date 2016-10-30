package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresMover;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Utils.Escritor;
import IA.Utils.Parametros;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class MainExperimental4 {
    public static void main(String[] argv) {
        Escritor escritor = new Escritor("resultadosExperimento4.txt");
        escritor.write("Proporción Tiempo NumOfertas Coste\n");
        for (int i = 0; i < 10; ++i) {
            int seed = (int)System.nanoTime();
            Paquetes paquetes = new Paquetes(100, seed);
            double proporcion = 1.2;
            for (int j = 0; j < 15; ++j) {
                Transporte ofertas = new Transporte(paquetes, proporcion, seed);
                Estado.setOfertas(ofertas);
                Estado.setPaquetes(paquetes);
                Estado estadoInicial = new Estado(seed);
                Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMover(), state -> true, new FuncionHeuristicaPrecio());
                HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
                try {
                    long time = System.currentTimeMillis();
                    SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
                    long time2 = System.currentTimeMillis();
                    Estado estadoFinal = (Estado)hillClimbingSearch.getGoalState();
                    time = time2 - time;
                    System.out.println(proporcion + " " + time + " " + ofertas.size() + " " + estadoFinal.getPrecio());
                    escritor.write(proporcion + " " + time + " " + ofertas.size() + " " + estadoFinal.getPrecio() + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                proporcion += 0.2;
            }
        }
        escritor.close();
    }
}
