package IA;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.Properties;

public class MainExperimentoSA {
    public static void main(String[] argv) {
        Paquetes paquetes = new Paquetes(100, Parametros.seed);
        Transporte ofertas = new Transporte(paquetes, 1.2, Parametros.seed);
        Estado.setOfertas(ofertas);
        Estado.setPaquetes(paquetes);
        Estado estadoInicial = new Estado(Parametros.seed);
        System.out.println(estadoInicial);

        GeneradorSucesoresSimulatedAnnealing generadorSucesoresSimulatedAnnealing = new GeneradorSucesoresSimulatedAnnealing(Parametros.seed);
        GeneradorSucesoresHillClimbing generadorSucesoresHillClimbing = new GeneradorSucesoresHillClimbing();
        Problem problemA = new Problem(estadoInicial, generadorSucesoresSimulatedAnnealing, state -> true, new FuncionHeuristica());
        Problem problemH = new Problem(estadoInicial, generadorSucesoresHillClimbing, state -> true, new FuncionHeuristica());
        SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(4000, 20, 5, 0.001);
        HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
        System.out.println("Estado inicial: felicidad " + estadoInicial.getFelicidad() + ", precio " + estadoInicial.getPrecio());
        try {
            System.out.println("HEURISTICO NORMAL:");
            System.out.println("Starting Simulated Annealing");
            SearchAgent agent = new SearchAgent(problemA, simulatedAnnealingSearch);
            Estado estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();
            System.out.println("Finished Simulated Annealing");
            System.out.println("Simulated Annealing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());

            System.out.println("Starting Hill Climbing");
            agent = new SearchAgent(problemH, hillClimbingSearch);
            estadoFinal = (Estado) hillClimbingSearch.getGoalState();
            System.out.println("Finished Hill Climbing");
            System.out.println("Hill Climbing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
            /*for (int i = 0; i < agent.getActions().size(); ++i) {
                System.out.println(i + ": " + agent.getActions().get(i));
            }
            System.out.println("Bla bla bla mr freeman " + agent.getActions().size());
            printInstrumentation(agent.getInstrumentation());*/
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