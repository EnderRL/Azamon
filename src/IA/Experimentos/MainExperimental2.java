package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresMover;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Generadores.GeneradorSucesoresHillClimbing;
import IA.Utils.Escritor;
import IA.Utils.Parametros;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

public class MainExperimental2 {
    public static void main(String[] argv) {

        Random rand = new Random(Parametros.seed);
        Escritor escritor = new Escritor("resultadosExperimento2.txt");
        escritor.write("Ordsol Ordpas Ordtp Randsol Randpas Randtp\n");

        for (int i = 0; i < 25; ++i) {
            int semilla = rand.nextInt();
            if (i != 0) System.out.println("-----------------------------------------------------");
            System.out.println("SEMILLA " + semilla +  ":");
            Paquetes paquetes = new Paquetes(100, semilla);
            Transporte ofertas = new Transporte(paquetes, 1.2,semilla);
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            GeneradorSucesoresMover generadorSucesoresMover = new GeneradorSucesoresMover();
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            SearchAgent agent;
            Estado estadoFinal;



            //ORDENADO
            Estado estadoInicialOrdenado = new Estado();
            Problem problemHOrdenado = new Problem(estadoInicialOrdenado, generadorSucesoresMover, state -> true, new FuncionHeuristicaPrecio());
            System.out.println("Estado inicial ordenado: felicidad " + estadoInicialOrdenado.getFelicidad() + ", precio " + estadoInicialOrdenado.getPrecio());
            try {
                System.out.println("ORDENADO");
                System.out.println("Starting Hill Climbing");
                long time = System.nanoTime();
                agent = new SearchAgent(problemHOrdenado, hillClimbingSearch);
                time = System.nanoTime()-time;
                estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                System.out.println("Finished Hill Climbing");
                //System.out.println(estadoFinal);
                String key = (String) agent.getInstrumentation().keySet().iterator().next();
                String property = agent.getInstrumentation().getProperty(key);
                System.out.println("PRECIO ORDENADO " + estadoFinal.getPrecio() + " " + property);
                escritor.write(estadoFinal.getPrecio() + " " + property + " " + Math.round(time/1000000) + " ");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //RANDOM
            double mediaPrecio = 0;
            long tiempoTotal = 0;
            int pasosTotal = 0;

            for (int j = 0; j < 5; ++j) {
                Estado estadoInicialRandom = new Estado((int) System.nanoTime());
                Problem problemHRandom = new Problem(estadoInicialRandom, generadorSucesoresMover, state -> true, new FuncionHeuristicaPrecio());
                System.out.println("Estado inicial random (iteracion " + j + "): felicidad " + estadoInicialRandom.getFelicidad() + ", precio " + estadoInicialRandom.getPrecio());
                try {
                    System.out.println("RANDOM " + j);
                    System.out.println("Starting Hill Climbing");
                    long timePrueba = System.nanoTime();
                    agent = new SearchAgent(problemHRandom, hillClimbingSearch);
                    tiempoTotal += System.nanoTime()-timePrueba;
                    estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                    System.out.println("Finished Hill Climbing");
                    //System.out.println(estadoFinal);
                    //System.out.println("Random (iteracion " + j + "): felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
                    mediaPrecio += estadoFinal.getPrecio();
                    String key = (String) agent.getInstrumentation().keySet().iterator().next();
                    String property = agent.getInstrumentation().getProperty(key);
                    pasosTotal += Integer.parseInt(property);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mediaPrecio = mediaPrecio/5;
            double mediaTiempo = tiempoTotal/5;
            double mediaPasos = pasosTotal/5;
            System.out.println("PRECIO RANDOM " + mediaPrecio);
            escritor.write(mediaPrecio + " " + mediaPasos + " " + Math.round(mediaTiempo/1000000) + "\n");
        }
        escritor.close();

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
