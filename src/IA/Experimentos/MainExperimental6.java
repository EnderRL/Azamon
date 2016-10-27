package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresHillClimbing;
import IA.Generadores.GeneradorSucesoresMover;
import IA.Heuristicos.FuncionHeuristica;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Utils.Escritor;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class MainExperimental6 {
    public static void main(String[] argv) {
        Escritor escritor = new Escritor("resultadosExperimento6.txt");
        escritor.write("Precio Felicidad Relacion Ponderacion Pasos Tiempo\n");

        for (int j = 0; j < 10; ++j) {
            int ponderacionFelicidad = 1;
            Paquetes paquetes = new Paquetes(100, (int) System.nanoTime());
            Transporte ofertas = new Transporte(paquetes, 1.2, (int) System.nanoTime());
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            Estado estadoInicial = new Estado((int) System.nanoTime());


            for (int i = 0; i < 25; ++i) {
                Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMover(), state -> true, new FuncionHeuristica(ponderacionFelicidad));
                HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();

                try {
                    long time = System.nanoTime();
                    SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
                    time = System.nanoTime() - time;
                    Estado estadoFinal = (Estado) hillClimbingSearch.getGoalState();
                    String key = (String) searchAgent.getInstrumentation().keySet().iterator().next();
                    String property = searchAgent.getInstrumentation().getProperty(key);
                    escritor.write(estadoFinal.getPrecio() + " " + estadoFinal.getFelicidad() + " " + estadoFinal.getPrecio()/estadoFinal.getFelicidad() + " " + ponderacionFelicidad + " " + property + " " + Math.round(time / 1000000) + "\n");
                    System.out.println("Felicidad: " + estadoFinal.getFelicidad() + " nodes expanded " + property + " Ponderacion: " + ponderacionFelicidad);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ++ponderacionFelicidad;
            }

        }
        escritor.close();
    }
}
