package utils;

public class Utileria {

    public static int GeneraCodigo() {
        return GeneraAleatoreo(1000, 9999);
    }

    public static int GeneraAleatoreo(int inicio, int fin) {
        return (int) (Math.random() * (fin - inicio + 1) + inicio);
    }
    
    public static double Redondear(double valor){
        return Math.round(valor * 10) / 10.0;
    }
}
