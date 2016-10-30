package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresMover;
import IA.Generadores.GeneradorSucesoresMoverSA;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Utils.Escritor;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Fanz0 on 30/10/2016.
 */
public class MainExperimental3SAvsHC {
    public static void main(String[] argv) {
        Escritor escritor = new Escritor("resultadosExperimento3SAvsHC.txt");
        long mediaTiempoHC = 0;
        long mediaTiempoSA = 0;
        float mediaPrecioHC = 0;
        float mediaPrecioSA = 0;
        for (int i = 0; i < 25; ++i) {
            Paquetes paquetes = new Paquetes(100, (int) System.nanoTime());
            Transporte ofertas = new Transporte(paquetes, 1.2, (int) System.nanoTime());
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            //Generamos Solucion INICIAL
            Estado estadoInicial = new Estado((int) System.nanoTime());

            Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMoverSA((int) System.nanoTime()), state -> true, new FuncionHeuristicaPrecio());
            Problem problem1 = new Problem(estadoInicial, new GeneradorSucesoresMover(), state -> true, new FuncionHeuristicaPrecio());

            try {
                //REPETIMOS PARA HACER MEDIAS
                double mediaPrecio = 0;
                long mediaTiempo = 0;
                SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(10000, 100, 5, 0.01);
                for (int j = 0; j < 5; ++j) {
                    long time = System.nanoTime();
                    SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
                    mediaTiempo += System.nanoTime() - time;
                    Estado estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();
                    mediaPrecio += estadoFinal.getPrecio();
                }
                mediaPrecioSA += mediaPrecio/5;
                mediaTiempoSA += mediaTiempo/5;
                HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
                long time = System.nanoTime();
                SearchAgent searchAgent = new SearchAgent(problem1, hillClimbingSearch);
                time = System.nanoTime() - time;
                Estado estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                mediaTiempoHC += time;
                mediaPrecioHC += estadoFinal.getPrecio();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        escritor.write("HCTIME HCCOSTE SATIME SACOST\n");
        escritor.write(mediaTiempoHC/25000000 + " " + mediaPrecioHC/25 + " " + mediaTiempoSA/25000000 + " " + mediaPrecioSA/25 + "\n");
        escritor.close();
    }
}
