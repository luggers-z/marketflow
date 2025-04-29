package dev.luggers;

public class Kraftwerk {
    Kraftwerk nf;
    Speicherbecken pool;
    double wirkungsgrad;
    double durchfluss;
    double maxdurchfluss;
    double mindurchfluss;
    double hoehe;


    Kraftwerk(Kraftwerk nachfolger, Speicherbecken pool, double hoehe, double wirkungsgrad) {
        nf = nachfolger;
        this.pool = pool;
        this.hoehe = hoehe;
        this.wirkungsgrad = wirkungsgrad;
    }
    public double fluss(double zufluss, double strompreis, double delta){
        double fluss = (zufluss-durchfluss)*delta;
        if(!pool.berechnen(fluss)) durchfluss = zufluss;
        if (nf != null) {
            return (gewinn(durchfluss, strompreis, delta) + nf.fluss(durchfluss, strompreis, delta));
        } else {
            return gewinn(durchfluss, strompreis, delta);
        }
    }
    public double gewinn(double durchfluss,double strompreis, double delta){
        double energie= hoehe*durchfluss*delta*1000*9.81*wirkungsgrad;
        double kwh= energie/3600000;
        return kwh*strompreis;
    }
}
