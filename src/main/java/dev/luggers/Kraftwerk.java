package dev.luggers;

public class Kraftwerk {
    Kraftwerk nf;
    Speicherbecken pool;
    double wirkungsgrad=1;
    double durchfluss;
    double maxdurchfluss;
    double mindurchfluss;



    Kraftwerk(Kraftwerk nachfolger, Speicherbecken pool) {
        nf = nachfolger;
        this.pool = pool;
    }
    public double fluss(double zufluss, double strompreis, double delta){
        pool.berechnen(zufluss-durchfluss);
        if(nf!=null){
            return(gewinn(durchfluss, strompreis, delta)+nf.fluss(durchfluss,strompreis,delta));
        }
        else{
            return gewinn(durchfluss, strompreis, delta);
        }

    }
    public double gewinn(double durchfluss,double strompreis, double delta){
        //implementieren
        return 1;
    }
}
