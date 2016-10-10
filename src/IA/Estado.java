package IA;

import IA.Azamon.*;

import java.util.*;
import java.util.concurrent.TransferQueue;

public class Estado {
    private double precio;
    private int felicidad;

    private ArrayList<Integer> asignacionPaquetes;



    private ArrayList<Double> pesoOfertas;

    private static Transporte ofertas;



    private static Paquetes paquetes;


    public Estado(ArrayList<Integer> asignacionPaquetes, ArrayList<Double> pesoOfertas, int felicidad, double precio) {
        this.asignacionPaquetes = asignacionPaquetes;
        this.pesoOfertas = pesoOfertas;
        this.felicidad = felicidad;
        this.precio = precio;
    }

    public static boolean calculaDias(int prioridad, int diasEntrega) {
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

    public static double calculaPrecio(int numeroOferta, Paquete p) {
        double precioPaquete = 0;
        switch (ofertas.get(numeroOferta).getDias()) {
            case 3:
                precioPaquete += 0.25*p.getPeso();
                break;
            case 4:
                precioPaquete += 0.25*p.getPeso();
                break;
            case 5:
                precioPaquete += 0.5*p.getPeso();
                break;
            default:
        }
        precioPaquete += ofertas.get(numeroOferta).getPrecio()*p.getPeso();
        return precioPaquete;
    }

    public static int calculaFelicidad(int numeroOferta, Paquete p) {
        int felicidad = 0;
        if (p.getPrioridad() == Paquete.PR2) {
            felicidad += 3-ofertas.get(numeroOferta).getDias();
        }
        else if (p.getPrioridad() == Paquete.PR3) {
            felicidad += 5-ofertas.get(numeroOferta).getDias();
        }
        return felicidad;
    }

    private boolean metePaquete(int paqueteI, Random random, ArrayList<PaqueteInteger> paquetesOrdenados) {
        if (paqueteI == paquetes.size()) return true;
        Paquete paquete = paquetesOrdenados.get(paqueteI).getPaquete();
        ArrayList<Integer> conjuntoOfertas = new ArrayList<>(ofertas.size());
        for (int i = 0; i < ofertas.size(); ++i) {
            if (pesoOfertas.get(i) + paquete.getPeso() <= ofertas.get(i).getPesomax() && calculaDias(paquete.getPrioridad(), ofertas.get(i).getDias())) {
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
            int ofertaEscogida = conjuntoOfertas.get(transporteRandom);
            asignacionPaquetes.set(paquetesOrdenados.get(paqueteI).getIndice(),ofertaEscogida);
            pesoOfertas.set(ofertaEscogida,pesoOfertas.get(ofertaEscogida)+paquete.getPeso());
            //Calculo del precio
            precio += calculaPrecio(ofertaEscogida,paquete);
            felicidad += calculaFelicidad(ofertaEscogida,paquete);
            if(metePaquete(paqueteI + 1,random, paquetesOrdenados)) return  true;
            asignacionPaquetes.set(paqueteI,-1);
            pesoOfertas.set(ofertaEscogida,pesoOfertas.get(ofertaEscogida)-paquete.getPeso());
            precio -= calculaPrecio(ofertaEscogida,paquete);
            felicidad -= calculaFelicidad(ofertaEscogida,paquete);
            conjuntoOfertas.remove(transporteRandom);
        }
        return false;
    }

    public Estado(int seed) {
        pesoOfertas = new ArrayList<>(ofertas.size());
        for (int i = 0; i < ofertas.size(); ++i) {
            pesoOfertas.add(0.0);
        }
        asignacionPaquetes = new ArrayList<>(paquetes.size());
        for (int i = 0; i < paquetes.size(); ++i) {
            asignacionPaquetes.add(-1);
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

    public int getFelicidad() {
        return felicidad;
    }

    public double getPrecio() {
        return precio;
    }

    public ArrayList<Integer> getAsignacionPaquetes() {
        return asignacionPaquetes;
    }

    public ArrayList<Double> getPesoOfertas() {
        return pesoOfertas;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Número de ofertas de transporte: " + pesoOfertas.size() + " || Felicidad: " + felicidad + " || Precio: " + precio + "\n";
        for (int i = 0; i < ofertas.size(); ++i) {
            s += "Oferta número " + i + " con peso " + pesoOfertas.get(i) + "/" + ofertas.get(i).getPesomax() + ", con dias de entrega " + ofertas.get(i).getDias() +  " y con precio: " + ofertas.get(i).getPrecio() + ":\n";
            for (int p = 0; p < asignacionPaquetes.size(); ++p) {
                if( asignacionPaquetes.get(p) == i) s += "\tIndice: " + p + " " +  paquetes.get(p) + "\n";
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

    public static Paquetes getPaquetes() { return paquetes; }

    public static Transporte getOfertas() { return ofertas; }

}
