package IA;

import IA.Azamon.*;

import java.util.*;

public class Estado {
    private double precio;
    private int felicidad;

    private ArrayList<PaqueMap> paquetesOfertados;

    private static Transporte ofertas;
    private static Paquetes paquetes;


    public Estado(ArrayList<PaqueMap> paquetesOfertados, int felicidad, int precio) {
        this.paquetesOfertados = paquetesOfertados;
        this.felicidad = felicidad;
        this.precio = precio;
    }

    private boolean calculaDias(int prioridad, int diasEntrega) {
        switch (prioridad) {
            case Paquete.PR1:
                return diasEntrega == 1;
            case Paquete.PR2:
                return diasEntrega <= 3;
            case Paquete.PR3:
                return diasEntrega <= 5;
            default:
                System.out.println("Prioridad incorrecta." + prioridad);

        }
        return false;
    }

    private boolean metePaquete(int paqueteI, Random random, ArrayList<PaqueteInteger> paquetesOrdenados) {
        if (paqueteI == paquetes.size()) return true;
        Paquete paquete = paquetesOrdenados.get(paqueteI).getPaquete();
        //System.out.println("Paquete número " + paqueteI + ": " + paquete);
        ArrayList<Integer> conjuntoOfertas = new ArrayList<>(ofertas.size());
        for (int i = 0; i < ofertas.size(); ++i) {
            if (paquetesOfertados.get(i).getPeso() + paquete.getPeso() <= ofertas.get(i).getPesomax() && calculaDias(paquete.getPrioridad()+1, ofertas.get(i).getDias())) {
                conjuntoOfertas.add(i);
            }
        }
        /*if (conjuntoOfertas.size() == 0) {
            System.out.println("Mala situación del paquete " + paqueteI + ": " + paquete);
            System.out.print(toString());
            Scanner sc = new Scanner(System.in);
            sc.next();
        }*/
        while (conjuntoOfertas.size() > 0) {
            int transporteRandom = random.nextInt(conjuntoOfertas.size());
            PaqueMap paquetesOferta = paquetesOfertados.get(conjuntoOfertas.get(transporteRandom));
            paquetesOferta.put(paqueteI, paquete);
            if(metePaquete(paqueteI + 1,random, paquetesOrdenados)) return  true;
            paquetesOferta.remove(paqueteI);
            conjuntoOfertas.remove(transporteRandom);
        }
        return false;
    }

    public Estado(int seed) {
        paquetesOfertados = new ArrayList<>(ofertas.size());
        for (int i = 0; i < ofertas.size(); ++i) {
            paquetesOfertados.add(new PaqueMap());
        }
        Random random = new Random(seed);
        ArrayList<PaqueteInteger> paquetesOrdenados = new ArrayList<>(paquetes.size());
        for (int i = 0; i < paquetes.size(); ++i) {
            paquetesOrdenados.add(new PaqueteInteger(i, paquetes.get(i)));
        }
        paquetesOrdenados.sort((o1, o2) -> {
            int comparacion = Integer.compare(o1.getPrioridad(), o2.getPrioridad());
            if (comparacion == 0) return Integer.compare(o1.getIndice(), o2.getIndice());
            return comparacion;
        });
        if (metePaquete(0, random, paquetesOrdenados)) System.out.println("Success");
        else System.out.println("Failure");
    }

    public double getFelicidad() {
        return felicidad;
    }

    public double getPrecio() {
        return precio;
    }

    public ArrayList<PaqueMap> getPaquetesOfertados() {
        return paquetesOfertados;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Número de ofertas de transporte: " + paquetesOfertados.size() + "\n";
        for (int i = 0; i < paquetesOfertados.size(); ++i) {
            s += "Oferta número " + i + " con peso " + paquetesOfertados.get(i).getPeso() + "/" + ofertas.get(i).getPesomax() + " i con dias de entrega " + ofertas.get(i).getDias() +  ":\n";
            for (Map.Entry<Integer,Paquete> p : paquetesOfertados.get(i).entrySet()) {
                s += "\t" +  p.getValue() + "\n";
            }
        }
        return s;
    }

    public static void setOfertas(Transporte ofertas) {
        Estado.ofertas = ofertas;
    }

    public static void setPaquetes(Paquetes paquetes) {
        Estado.paquetes = paquetes;
    }

}
