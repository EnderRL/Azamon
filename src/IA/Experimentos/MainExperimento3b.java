package IA.Experimentos;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import IA.Estado;
import IA.Generadores.GeneradorSucesoresMoverSA;
import IA.Heuristicos.FuncionHeuristicaPrecio;
import IA.Utils.Escritor;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.SimulatedAnnealingSearch;

/**
 * Created by mario.fernandez on 27/10/2016.
 */
public class MainExperimento3b {
    public static void main(String[] argv) {
        Escritor escritor = new Escritor("resultadosExperimento3b.txt");
        escritor.write("K LAMBDA PRECIO PASOS TIEMPO\n");
        for (int i = 0; i < 25; ++i) {
            Paquetes paquetes = new Paquetes(100, (int)System.nanoTime());
            Transporte ofertas = new Transporte(paquetes, 1.2, (int)System.nanoTime());
            Estado.setOfertas(ofertas);
            Estado.setPaquetes(paquetes);
            //Generamos Solucion INICIAL
            Estado estadoInicial = new Estado((int)System.nanoTime());
            int k;
            double lambda;


            Problem problem = new Problem(estadoInicial, new GeneradorSucesoresMoverSA((int)System.nanoTime()), state -> true, new FuncionHeuristicaPrecio());
            try {
                //LAMBDA FIJA EN 0.0001
                lambda = 0.001;
                //VARIAMOS K
                for (k = 5; k <= 50000; k *= 10) {
                    //REPETIMOS PARA HACER MEDIAS
                    double mediaPrecio = 0;
                    int mediaPasos = 0;
                    long mediaTiempo = 0;
                    SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(1400,100,k,lambda);
                    for (int j = 0; j < 5; ++j) {
                        long time = System.nanoTime();
                        SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
                        mediaTiempo += System.nanoTime() - time;
                        Estado estadoFinal = (Estado) simulatedAnnealingSearch.getGoalState();
                        String key = (String) searchAgent.getInstrumentation().keySet().iterator().next();
                        String property = searchAgent.getInstrumentation().getProperty(key);
                        mediaPasos += Integer.parseInt(property);
                        mediaPrecio += estadoFinal.getPrecio();
                    }
                    escritor.write(k + " " + lambda + " " + mediaPrecio/5 + " " + mediaPasos + " " + Math.round(mediaTiempo/5000000) + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            escritor.write("\n");
        }
        escritor.close();
    }
}
