package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresMover;
import IA.Generadores.GeneradorSucesoresMoverSA;
import IA.Heuristicos.FuncionHeuristica;
import IA.Utils.Escritor;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class MainExperimental7 {
    public static void main(String[] argv) {
        Escritor escritor = new Escritor("resultadosExperimento7.txt");
        escritor.write("Precio Felicidad Relacion Ponderacion Tiempo\n");

        for (int j = 0; j < 10; ++j) {
            int ponderacionFelicidad = 1;
            Paquetes paquetes = new Paquetes(100, (int) System.nanoTime());
            Transporte ofertas = new Transporte(paquetes, 1.2, (int) System.nanoTime());
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            Estado estadoInicial = new Estado((int) System.nanoTime());


            for (int i = 0; i < 25; ++i) {
                Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMoverSA((int)System.nanoTime()), state -> true, new FuncionHeuristica(ponderacionFelicidad));
                SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(1400,100,5,0.001);
                try {
                    int mediaFelicidad = 0;
                    long mediaTiempo = 0;
                    double mediaPrecio = 0;
                    for (int k = 0; k < 5; ++k) {
                        long time = System.nanoTime();
                        SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
                        mediaTiempo += System.nanoTime() - time;
                        Estado estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();
                        mediaFelicidad += estadoFinal.getFelicidad();
                        mediaPrecio += estadoFinal.getPrecio();
                    }
                    escritor.write(mediaPrecio/5 + " " + mediaFelicidad/5 + " " + mediaPrecio/mediaFelicidad + " " + ponderacionFelicidad +  " " + Math.round(mediaTiempo / 5000000) + "\n");
                    System.out.println(mediaPrecio/5 + " " + mediaFelicidad/5 + " " + mediaPrecio/mediaFelicidad + " " + ponderacionFelicidad +  " " + Math.round(mediaTiempo / 5000000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ++ponderacionFelicidad;
            }

        }
        escritor.close();
    }
}
