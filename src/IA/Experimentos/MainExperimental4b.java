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

public class MainExperimental4b {
    public static void main(String[] argv) {
        Escritor escritor = new Escritor("resultadosExperimento4b.txt");
        escritor.write("Paquetes Tiempo\n");
        for (int i = 0; i < 10; ++i) {
            int numPaquetes = 100;
            for (int j = 0; j < 5; ++j) {
                Paquetes paquetes = new Paquetes(numPaquetes, (int) System.nanoTime());
                Transporte ofertas = new Transporte(paquetes, 1.2, (int) System.nanoTime());
                Estado.setOfertas(ofertas);
                Estado.setPaquetes(paquetes);
                Estado estadoInicial = new Estado((int) System.nanoTime());
                Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMover(), state -> true, new FuncionHeuristicaPrecio());
                HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
                try {
                    long time = System.currentTimeMillis();
                    SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
                    long time2 = System.currentTimeMillis();
                    Estado estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                    time = time2 - time;
                    System.out.println(numPaquetes + " " + time);
                    escritor.write(numPaquetes + " " + time + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                numPaquetes += 50;
            }
        }
        escritor.close();
    }
}
