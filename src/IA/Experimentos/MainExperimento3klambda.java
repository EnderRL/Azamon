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
import java.util.Collections;

/**
 * Created by mario.fernandez on 27/10/2016.
 */
public class MainExperimento3klambda {

    //5 -> 0
    //50 -> 1
    //500 -> 2
    //5000 -> 3
    //50000 -> 4

    private static int hash(int x) {
        return (int)Math.log10(x/5);
    }

    //0.1 -> 0
    //0.01 -> 1
    //0.001 -> 2
    //0.0001 -> 3

    private static int hash(double d) {
        return -((int)Math.log10(d) + 1);
    }

    public static void main(String[] argv) {
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>(5);
        for (int i = 0; i < 5; ++i) {
            ArrayList<Double> vecaux = new ArrayList<>(Collections.nCopies(4,0.));
            matrix.add(i,vecaux);
        }
        Escritor escritor = new Escritor("resultadosExperimento3klambda.txt");
        double mediaHC = 0;

        for (int i = 0; i < 25; ++i) {

            Paquetes paquetes = new Paquetes(100, (int) System.nanoTime());
            Transporte ofertas = new Transporte(paquetes, 1.2, (int) System.nanoTime());
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            //Generamos Solucion INICIAL
            Estado estadoInicial = new Estado((int) System.nanoTime());
            int k ;
            double lambda ;


            Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMoverSA((int) System.nanoTime()), state -> true, new FuncionHeuristicaPrecio());
            Problem problem1 = new Problem(estadoInicial, new GeneradorSucesoresMover(), state -> true, new FuncionHeuristicaPrecio());
            try {
                //VARIAMOS LAMBDA
                for (lambda = 0.0001; lambda <= 0.1; lambda *= 10) {
                    //VARIAMOS K

                    for (k = 5; k <= 50000; k *= 10) {
                        //REPETIMOS PARA HACER MEDIAS
                        double mediaPrecio = 0;
                        SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(10000, 100, k, lambda);
                        for (int j = 0; j < 5; ++j) {
                            SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
                            Estado estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();
                            mediaPrecio += estadoFinal.getPrecio();
                        }

                        matrix.get(hash(k)).set(hash(lambda), matrix.get(hash(k)).get(hash(lambda))+mediaPrecio/5);

                    }
                }
                HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
                SearchAgent searchAgent = new SearchAgent(problem1,hillClimbingSearch);
                Estado estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                mediaHC += estadoFinal.getPrecio();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        escritor.write("K/LAMBDA 0.1 0.01 0.001 0.0001\n");
        for (int i = 0; i < 5; ++i) {
            escritor.write(((int)5*Math.pow(10,i)) + "");
            for (int j = 0; j < 4; ++j) {
                 escritor.write(" " + matrix.get(i).get(j)/25);
            }
            escritor.write("\n");
        }
        escritor.write("Media HC = " + mediaHC/25);
        escritor.close();
    }
}
