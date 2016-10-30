package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresHillClimbing;
import IA.Generadores.GeneradorSucesoresMover;
import IA.Generadores.GeneradorSucesoresMoverSA;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Utils.Escritor;
import IA.Utils.Parametros;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mario.fernandez on 27/10/2016.
 */
public class MainExperimental3DesgloseCostePasos {
    public static void main(String[] argv) {

            Paquetes paquetes = new Paquetes(100, (int) System.nanoTime());
            Transporte ofertas = new Transporte(paquetes, 1.2, (int) System.nanoTime());
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            //Generamos Solucion INICIAL
            Estado estadoInicial = new Estado((int) System.nanoTime());
            int k;
            double lambda;


            Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMoverSA((int) System.nanoTime()), state -> true, new FuncionHeuristicaPrecio());
            try {
                //K EN 5
                k = 5;
                //LAMBDA EN 0.001
                lambda = 0.001;
                Escritor escritor;
                escritor = new Escritor("resultadosExperimento3desglose.txt");
                escritor.write("PASOS PRECIO\n");

                SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(10000, 100, k, lambda);

                SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
                Estado estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();
                String key = (String) searchAgent.getInstrumentation().keySet().iterator().next();
                String property = searchAgent.getInstrumentation().getProperty(key);
                ArrayList<Double> costePaso = simulatedAnnealingSearch.getCostePaso();
                for (int i = 0; i < costePaso.size(); i += 2) {
                    escritor.write(costePaso.get(i) + " " + costePaso.get(i + 1) + "\n");
                }
                escritor.write( property + " " + estadoFinal.getPrecio() + "\n");
                escritor.close();



            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
