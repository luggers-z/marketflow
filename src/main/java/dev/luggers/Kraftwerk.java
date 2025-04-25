package dev.luggers;

public class Kraftwerk {
    Kraftwerk nf;
    Speicherbecken pool;
    double wirkungsgrad;
    double durchfluss;
    double maxdurchfluss;
    double mindurchfluss;
    long hoehe;


    Kraftwerk(Kraftwerk nachfolger, Speicherbecken pool, long hoehe, long wirkungsgrad) {
        nf = nachfolger;
        this.pool = pool;
        this.hoehe = hoehe;
        this.wirkungsgrad = wirkungsgrad;
    }
    public double fluss(double zufluss, double strompreis, double delta){
        if(!pool.berechnen(zufluss - durchfluss)) durchfluss = zufluss;
        if (nf != null) {
            return (gewinn(durchfluss, strompreis, delta) + nf.fluss(durchfluss, strompreis, delta));
        } else {
            return gewinn(durchfluss, strompreis, delta);
        }
    }
    public double gewinn(double durchfluss,double strompreis, double delta){
        long energie= (long) (hoehe*durchfluss*delta*1000*9.81*wirkungsgrad);
        long kwh= energie/3600000;
        return kwh*strompreis;
    }
}
