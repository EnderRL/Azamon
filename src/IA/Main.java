package IA;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;

public class Main {
    public static void main(String[] argv) {
        System.out.println("ESTO ES EL INICIO DE GLA2, que dominará el mundo");
        System.out.println("Creando paquetes. :D");
        Paquetes paquetes = new Paquetes(8, 0);
        System.out.println("Creando transportes. :D:D");
        Transporte ofertas = new Transporte(paquetes, 1, 0);
        Estado.setOfertas(ofertas);
        Estado.setPaquetes(paquetes);
        Estado estado = new Estado(0);
        System.out.println(estado);
    }
}

