package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Heuristicos.FuncionHeuristica;
import IA.Heuristicos.FuncionHeuristicaFelicidad;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Generadores.GeneradorSucesoresHillClimbing;
import IA.Generadores.GeneradorSucesoresSimulatedAnnealing;
import IA.Utils.Parametros;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.Properties;

public class Main {
    public static void main(String[] argv) {
        System.out.println("ESTO ES EL INICIO DE GLA2, que dominará el mundo");
        System.out.println("Creando paquetes. :D");
        Paquetes paquetes = new Paquetes(100, Parametros.seed);
        System.out.println("Creando transportes. :D:D");
        Transporte ofertas = new Transporte(paquetes, 1.2, Parametros.seed);
        Estado.setOfertas(ofertas);
        Estado.setPaquetes(paquetes);
        System.out.println("Creando estado inicial. :D:D:D");
        Estado estadoInicial = new Estado(Parametros.seed);
        System.out.println(estadoInicial);

        GeneradorSucesoresHillClimbing generadorSucesoresHillClimbing = new GeneradorSucesoresHillClimbing();
        GeneradorSucesoresSimulatedAnnealing generadorSucesoresSimulatedAnnealing = new GeneradorSucesoresSimulatedAnnealing(Parametros.seed);

        Problem problemH = new Problem(estadoInicial, generadorSucesoresHillClimbing, state -> true, new FuncionHeuristica());
        Problem problemHP = new Problem(estadoInicial, generadorSucesoresHillClimbing, state -> true, new FuncionHeuristicaPrecio());
        Problem problemHF = new Problem(estadoInicial, generadorSucesoresHillClimbing, state -> true, new FuncionHeuristicaFelicidad());

        Problem problemA = new Problem(estadoInicial, generadorSucesoresSimulatedAnnealing, state -> true, new FuncionHeuristica());
        Problem problemAP = new Problem(estadoInicial, generadorSucesoresSimulatedAnnealing, state -> true, new FuncionHeuristicaPrecio());
        Problem problemAF = new Problem(estadoInicial, generadorSucesoresSimulatedAnnealing, state -> true, new FuncionHeuristicaFelicidad());

        HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
        SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(4000, 100, 5, 0.001);
        System.out.println("Estado inicial: felicidad " + estadoInicial.getFelicidad() + ", precio " + estadoInicial.getPrecio());
        try {
            System.out.println("HEURISTICO NORMAL:");
            System.out.println("Starting Simulated Annealing");
            SearchAgent agent = new SearchAgent(problemA, simulatedAnnealingSearch);
            Estado estadoFinal = (Estado)simulatedAnnealingSearch.getGoalState();
            System.out.println("Finished Simulated Annealing");
            System.out.println("Simulated Annealing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
            System.out.println("Starting Hill Climbing");
            agent = new SearchAgent(problemH, hillClimbingSearch);
            estadoFinal = (Estado)hillClimbingSearch.getGoalState();
            System.out.println("Finished Hill Climbing");
            System.out.println("Hill climbing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
            //System.out.println(estadoFinal);

            System.out.println("HEURISTICO PRECIO");
            System.out.println("Starting Simulated Annealing");
            agent = new SearchAgent(problemAP, simulatedAnnealingSearch);
            estadoFinal = (Estado)simulatedAnnealingSearch.getGoalState();
            System.out.println("Finished Simulated Annealing");
            System.out.println("Simulated Annealing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
            System.out.println("Starting Hill Climbing");
            long time = System.nanoTime();
            agent = new SearchAgent(problemHP, hillClimbingSearch);
            time = System.nanoTime()-time;
            estadoFinal = (Estado)hillClimbingSearch.getGoalState();
            System.out.println("Finished Hill Climbing");
            System.out.println("Hill climbing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio() + ", tiempo " + Math.round(time/1000000));
            //System.out.println(estadoFinal);

            System.out.println("HEURISTICO FELICIDAD");
            System.out.println("Starting Simulated Annealing");
            agent = new SearchAgent(problemAF, simulatedAnnealingSearch);
            estadoFinal = (Estado)simulatedAnnealingSearch.getGoalState();
            System.out.println("Finished Simulated Annealing");
            System.out.println("Simulated Annealing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
            System.out.println("Starting Hill Climbing");
            agent = new SearchAgent(problemHF, hillClimbingSearch);
            estadoFinal = (Estado)hillClimbingSearch.getGoalState();
            System.out.println("Finished Hill Climbing");
            System.out.println("Hill climbing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
            //System.out.println(estadoFinal);

            System.out.println("Estado inicial: felicidad " + estadoInicial.getFelicidad() + ", precio " + estadoInicial.getPrecio());

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

