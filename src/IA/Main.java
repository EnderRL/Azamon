package IA;

import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;

import java.util.List;

public class Main {
    public static void main(String[] argv) {
        System.out.println("ESTO ES EL INICIO DE GLA2, que dominará el mundo");
        System.out.println("Creando paquetes. :D");
        Paquetes paquetes = new Paquetes(100, (int)Parametros.seed);
        System.out.println("Creando transportes. :D:D");
        Transporte ofertas = new Transporte(paquetes, 1.2, (int)Parametros.seed);
        Estado.setOfertas(ofertas);
        Estado.setPaquetes(paquetes);
        Estado estado = new Estado();
        System.out.println(estado);
        /*GeneradorSucesores generadorSucesores = new GeneradorSucesores();
        List estados = generadorSucesores.getSuccessors(estado);
        System.out.println(estados.size());*/
    }
}

