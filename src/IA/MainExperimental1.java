package IA;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class MainExperimental1 {
    public static void main(String[] argv) {
        Escritor escritor = new Escritor("resultadosExperimento1.txt");
        escritor.write("MovPrecio MovPasos MovTiempo MovIntPrecio MovIntPasos MovIntTiempo\n");
        for (int i = 0; i < 10; ++i) {
            System.out.println("ESTO ES EL INICIO DE GLA2, que dominará el mundo");
            System.out.println("Creando paquetes. :D");
            Paquetes paquetes = new Paquetes(100, (int)System.nanoTime());
            System.out.println("Creando transportes. :D:D");
            Transporte ofertas = new Transporte(paquetes, 1.2, (int)System.nanoTime());
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            System.out.println("Creando estado inicial. :D:D:D");
            Estado estadoInicial = new Estado((int)System.nanoTime());

            Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMover(), state -> true, new FuncionHeuristicaPrecio());
            Problem problem1 = new Problem(estadoInicial, new GeneradorSucesoresHillClimbing(), state -> true, new FuncionHeuristicaPrecio());
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();

            try {
                System.out.println("OPERADOR SOLO MOVER");
                long time = System.nanoTime();
                SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
                time = System.nanoTime()-time;
                Estado estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                String key = (String) searchAgent.getInstrumentation().keySet().iterator().next();
                String property = searchAgent.getInstrumentation().getProperty(key);
                escritor.write(estadoFinal.getPrecio() + " " + property + " " + Math.round(time/1000000) + " ");
                System.out.println("Precio: " + estadoFinal.getPrecio() + " nodes expanded " + property);
                System.out.println("OPERADOR MOVER INTERCAMBIAR");
                time = System.nanoTime();
                searchAgent = new SearchAgent(problem1, hillClimbingSearch);
                time = System.nanoTime()-time;
                estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                key = (String) searchAgent.getInstrumentation().keySet().iterator().next();
                property = searchAgent.getInstrumentation().getProperty(key);
                escritor.write(estadoFinal.getPrecio() + " " + property + " " + Math.round(time/1000000) + "\n");
                property = searchAgent.getInstrumentation().getProperty(key);
                System.out.println("Precio: " + estadoFinal.getPrecio() + " nodes expanded " + property);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        escritor.close();
    }
}
