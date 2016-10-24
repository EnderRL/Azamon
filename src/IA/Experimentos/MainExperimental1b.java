package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresMoverSA;
import IA.Generadores.GeneradorSucesoresSimulatedAnnealing;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Generadores.GeneradorSucesoresHillClimbing;
import IA.Generadores.GeneradorSucesoresMover;
import IA.Utils.Escritor;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class MainExperimental1b {
    public static void main(String[] argv) {
        Escritor escritor = new Escritor("resultadosExperimento1b.txt");
        escritor.write("MovPrecio MovTiempo MovIntPrecio MovIntTiempo\n");
        for (int i = 0; i < 25; ++i) {
            System.out.println("ESTO ES EL INICIO DE GLA2, que dominará el mundo");
            System.out.println("Creando paquetes. :D");
            Paquetes paquetes = new Paquetes(100, (int)System.nanoTime());
            System.out.println("Creando transportes. :D:D");
            Transporte ofertas = new Transporte(paquetes, 1.2, (int)System.nanoTime());
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            System.out.println("Creando estado inicial. :D:D:D");
            Estado estadoInicial = new Estado((int)System.nanoTime());

            Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMoverSA((int)System.nanoTime()), state -> true, new FuncionHeuristicaPrecio());
            Problem problem1 = new Problem(estadoInicial, new GeneradorSucesoresSimulatedAnnealing((int)System.nanoTime()), state -> true, new FuncionHeuristicaPrecio());
            SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch();

            try {
                double mediaPrecio = 0;
                long mediaTiempo = 0;
                double mediaPrecioMov = 0;
                double mediaTiempoMov = 0;
                for (int j = 0; j < 10; ++j) {
                    System.out.println("OPERADOR SOLO MOVER");
                    long time = System.nanoTime();
                    SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
                    time = System.nanoTime() - time;
                    Estado estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();
                    String key = (String) searchAgent.getInstrumentation().keySet().iterator().next();
                    String property = searchAgent.getInstrumentation().getProperty(key);
                    mediaPrecio += estadoFinal.getPrecio();
                    mediaTiempo += time;
                    System.out.println("Precio: " + estadoFinal.getPrecio() + " nodes expanded " + property);
                    System.out.println("OPERADOR MOVER INTERCAMBIAR");
                    time = System.nanoTime();
                    searchAgent = new SearchAgent(problem1, simulatedAnnealingSearch);
                    time = System.nanoTime() - time;
                    estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();
                    key = (String) searchAgent.getInstrumentation().keySet().iterator().next();
                    property = searchAgent.getInstrumentation().getProperty(key);
                    mediaPrecioMov += estadoFinal.getPrecio();
                    mediaTiempoMov += time;
                    property = searchAgent.getInstrumentation().getProperty(key);
                    System.out.println("Precio: " + estadoFinal.getPrecio() + " nodes expanded " + property);
                }
                escritor.write(mediaPrecio/10 + " " + Math.round(mediaTiempo / 10000000) + " ");
                escritor.write(mediaPrecioMov/10 + " " + Math.round(mediaTiempoMov / 10000000) + "\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        escritor.close();
    }
}
