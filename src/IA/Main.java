package IA;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import aima.search.framework.GoalTest;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.List;

public class Main {
    public static void main(String[] argv) {
        System.out.println("ESTO ES EL INICIO DE GLA2, que dominará el mundo");
        System.out.println("Creando paquetes. :D");
        Paquetes paquetes = new Paquetes(10, Parametros.seed);
        System.out.println("Creando transportes. :D:D");
        Transporte ofertas = new Transporte(paquetes, 1.2, Parametros.seed);
        Estado.setOfertas(ofertas);
        Estado.setPaquetes(paquetes);
        Estado estadoInicial = new Estado(Parametros.seed);
        System.out.println(estadoInicial);
        Problem problem = new Problem(estadoInicial, new GeneradorSucesoresHillClimbing(), state -> true, new FuncionHeuristica());
        HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
        try {
            List list = hillClimbingSearch.search(problem);
            System.out.println("Finished");
            for (Object o : list) System.out.println(o);
            System.out.println(hillClimbingSearch.getGoalState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

