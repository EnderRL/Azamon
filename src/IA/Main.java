package IA;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import aima.search.framework.GoalTest;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] argv) {
        System.out.println("ESTO ES EL INICIO DE GLA2, que dominará el mundo");
        System.out.println("Creando paquetes. :D");
        Paquetes paquetes = new Paquetes(10, Parametros.seed);
        System.out.println("Creando transportes. :D:D");
        Transporte ofertas = new Transporte(paquetes, 1.2, Parametros.seed);
        Estado.setOfertas(ofertas);
        Estado.setPaquetes(paquetes);
        Estado estadoInicial = new Estado(Parametros.seed);
        System.out.println(estadoInicial);
        Problem problemH = new Problem(estadoInicial, new GeneradorSucesoresHillClimbing(), state -> true, new FuncionHeuristica());
        Problem problemA = new Problem(estadoInicial, new GeneradorSucesoresSimulatedAnnealing(Parametros.seed), state -> true, new FuncionHeuristica());
        HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
        SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(2000,100,5,0.001);
        try {
            System.out.println("Starting Simulated Annealing");
            SearchAgent agent = new SearchAgent(problemA, simulatedAnnealingSearch);
            System.out.println("Finished Simulated Annealing");
            System.out.println("Actions");
            for (Object o : agent.getActions()) System.out.println(o);
            System.out.println("Instrumentation");
            printInstrumentation(agent.getInstrumentation());
            System.out.println("Goal state Simulated Annealing");
            System.out.println(simulatedAnnealingSearch.getGoalState());
            /*System.out.println("Starting Hill Climbing");
            List listH = hillClimbingSearch.search(problemH);
            System.out.println("Finished Hill Climbing");
            for (Object o : listH) System.out.println(o);
            System.out.println("Goal State Hill Climbing");
            System.out.println(hillClimbingSearch.getGoalState());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }
}

