package IA;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import aima.basic.Util;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.Properties;

public class MainExperimental {
    public static void main(String[] argv) {
        System.out.println("Creando paquetes.");
        Paquetes paquetes = new Paquetes(100, Parametros.seed);
        System.out.println("Final creación paquetes.");
        System.out.println("Creando transportes.");
        Transporte ofertas = new Transporte(paquetes, 1.2, Parametros.seed);
        Estado.setOfertas(ofertas);
        Estado.setPaquetes(paquetes);
        System.out.println("Final creación transportes.");
        System.out.println("Creando estado inicial.");
        Estado estadoInicial = new Estado(Parametros.seed);
        System.out.println("Final creación estado inicial.");
        System.out.println(estadoInicial);
        double kMax = -1, lambdaMax = -1;
        Estado estadoMax = null;
        GeneradorSucesoresSimulatedAnnealing generadorSucesoresSimulatedAnnealing = new GeneradorSucesoresSimulatedAnnealing(Parametros.seed);
        FuncionHeuristica funcionHeuristica = new FuncionHeuristica();
        Problem problemA = new Problem(estadoInicial, generadorSucesoresSimulatedAnnealing, state -> true, new FuncionHeuristica());
        //Problem problemAP = new Problem(estadoInicial, generadorSucesoresSimulatedAnnealing, state -> true, new FuncionHeuristicaPrecio());
        double heuristico = Double.MAX_VALUE;
        for (int k = 5; k <= 5000; k *= 10) {
            for (double lambda = 0.0005; lambda <= 0.5; lambda *= 10) {
                System.out.println("K = " + k + " LAMBDA =  " + lambda);
                SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(8000, 100, k, lambda);
                System.out.println("Estado inicial: felicidad " + estadoInicial.getFelicidad() + ", precio " + estadoInicial.getPrecio());
                try {
                    System.out.println("HEURISTICO NORMAL:");
                    System.out.println("Starting Simulated Annealing");
                    SearchAgent agent = new SearchAgent(problemA, simulatedAnnealingSearch);
                    Estado estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();
                    System.out.println("Finished Simulated Annealing");
                    System.out.println("Simulated Annealing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
                    double heuristicoFinal = funcionHeuristica.getHeuristicValue(estadoFinal);
                    if (heuristicoFinal < heuristico) {
                        heuristico = heuristicoFinal;
                        kMax = k;
                        lambdaMax = lambda;
                        estadoMax = estadoFinal;
                    }
                    //System.out.println(estadoFinal);
            /*System.out.println("HEURISTICO PRECIO");
            System.out.println("Starting Simulated Annealing");
            agent = new SearchAgent(problemAP, simulatedAnnealingSearch);
            estadoFinal = (Estado)simulatedAnnealingSearch.getGoalState();
            System.out.println("Finished Simulated Annealing");
            System.out.println("Simulated Annealing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
            System.out.println("Starting Hill Climbing");
            agent = new SearchAgent(problemHP, hillClimbingSearch);
            estadoFinal = (Estado)hillClimbingSearch.getGoalState();
            System.out.println("Finished Hill Climbing");
            System.out.println("Hill climbing: felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
            //System.out.println(estadoFinal);*/


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println("Reporte final: mejor heuristico con lambda: " + lambdaMax + " y k: " + kMax + " estado: " + estadoMax);
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
