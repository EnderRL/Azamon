package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresMover;
import IA.Generadores.GeneradorSucesoresMoverSA;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Utils.Escritor;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario.fernandez on 27/10/2016.
 */
public class MainExperimento3d {
    public static void main(String[] argv) {
        Escritor escritor = new Escritor("resultadosExperimento3dSA.txt");
        Escritor escritor1 = new Escritor("resultadosExperimento3dHC.txt");
        escritor.write("PASOS PRECIO\n");
        escritor1.write("PASOS PRECIO\n");
        Paquetes paquetes = new Paquetes(100, (int)System.nanoTime());
        Transporte ofertas = new Transporte(paquetes, 1.2, (int)System.nanoTime());
        Estado.setOfertas(ofertas);
        Estado.setPaquetes(paquetes);
        //Generamos Solucion INICIAL
        Estado estadoInicial = new Estado((int)System.nanoTime());
        int k = 5;
        double lambda = 0.01;


        Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMoverSA((int)System.nanoTime()), state -> true, new FuncionHeuristicaPrecio());

        Problem problem1 = new Problem(estadoInicial, new GeneradorSucesoresMover(),state -> true, new FuncionHeuristicaPrecio());

        try {
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            long tiempo = System.nanoTime();
            SearchAgent agent = new SearchAgent(problem1,hillClimbingSearch);
            tiempo = System.nanoTime() - tiempo;
            Estado estadoFinal = (Estado) hillClimbingSearch.getGoalState();
            double precio = estadoFinal.getPrecio();
            ArrayList<Double> listaHC = hillClimbingSearch.getCostePaso();
            for (int j = 0; j < listaHC.size(); j+=2) {
               escritor1.write(listaHC.get(j) + " " + listaHC.get(j+1) + "\n");
            }
            String key = (String) agent.getInstrumentation().keySet().iterator().next();
            String property = agent.getInstrumentation().getProperty(key);
            escritor1.write(property + " " + precio + "\n");
            escritor1.close();
            SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(4000,100,k,lambda);

            tiempo = System.nanoTime();
            agent = new SearchAgent(problem,simulatedAnnealingSearch);
            tiempo = System.nanoTime() - tiempo;
            estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();

            listaHC = simulatedAnnealingSearch.getCostePaso();
            for (int j = 0; j < listaHC.size(); j+=2) {
                escritor.write(listaHC.get(j) + " " + listaHC.get(j+1) + "\n");
            }
            key = (String) agent.getInstrumentation().keySet().iterator().next();
            property = agent.getInstrumentation().getProperty(key);
            precio = estadoFinal.getPrecio();
            escritor.write(property + " " + precio + "\n");
            escritor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
