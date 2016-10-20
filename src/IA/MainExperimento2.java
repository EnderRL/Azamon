package IA;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

public class MainExperimento2 {
    public static void main(String[] argv) {

        Random rand = new Random(Parametros.seed);
        Escritor escritor = new Escritor("resultadosExperimento2.txt");
        escritor.write("Ord Rand\n");

        for (int i = 0; i < 10; ++i) {
            int semilla = rand.nextInt();
            if (i != 0) System.out.println("-----------------------------------------------------");
            System.out.println("SEMILLA " + semilla +  ":");
            Paquetes paquetes = new Paquetes(100, semilla);
            Transporte ofertas = new Transporte(paquetes, 1.2,semilla);
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);

            GeneradorSucesoresHillClimbing generadorSucesoresHillClimbing = new GeneradorSucesoresHillClimbing();
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            SearchAgent agent;
            Estado estadoFinal;



            //ORDENADO
            Estado estadoInicialOrdenado = new Estado();
            Problem problemHOrdenado = new Problem(estadoInicialOrdenado, generadorSucesoresHillClimbing, state -> true, new FuncionHeuristicaPrecio());
            System.out.println("Estado inicial ordenado: felicidad " + estadoInicialOrdenado.getFelicidad() + ", precio " + estadoInicialOrdenado.getPrecio());
            try {
                System.out.println("ORDENADO");
                System.out.println("Starting Hill Climbing");
                agent = new SearchAgent(problemHOrdenado, hillClimbingSearch);
                estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                System.out.println("Finished Hill Climbing");
                //System.out.println(estadoFinal);
                System.out.println("PRECIO ORDENADO " + estadoFinal.getPrecio());
                escritor.write(estadoFinal.getPrecio() + " ");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //RANDOM
            double mediaPrecio = 0;

            for (int j = 0; j < 5; ++j) {
                Estado estadoInicialRandom = new Estado((int) System.nanoTime());
                Problem problemHRandom = new Problem(estadoInicialRandom, generadorSucesoresHillClimbing, state -> true, new FuncionHeuristicaPrecio());
                System.out.println("Estado inicial random (iteracion " + j + "): felicidad " + estadoInicialRandom.getFelicidad() + ", precio " + estadoInicialRandom.getPrecio());
                try {
                    System.out.println("RANDOM " + j);
                    System.out.println("Starting Hill Climbing");
                    agent = new SearchAgent(problemHRandom, hillClimbingSearch);
                    estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                    System.out.println("Finished Hill Climbing");
                    //System.out.println(estadoFinal);
                    //System.out.println("Random (iteracion " + j + "): felicidad: " + estadoFinal.getFelicidad() + ", precio " + estadoFinal.getPrecio());
                    System.out.println("HEY! Estoy sumando " + estadoFinal.getPrecio());
                    mediaPrecio += estadoFinal.getPrecio();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mediaPrecio = mediaPrecio/5;
            System.out.println("PRECIO RANDOM " + mediaPrecio);
            escritor.write(mediaPrecio + "\n");
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
