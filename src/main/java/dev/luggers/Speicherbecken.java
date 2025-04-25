package dev.luggers;

public class Speicherbecken {
    long l;
    long b;
    double h;
    double vol;
    long minh;
    long maxh;
    Speicherbecken nf;
    Speicherbecken(Speicherbecken nachfolger, long l, long b, double h,long minh, long maxh) {
        nf = nachfolger;
        this.l = l;
        this.b = b;
        this.h = h;
        this.vol = l*b*h;
        this.minh = minh;
        this.maxh = maxh;
    }
    public boolean berechnen(double zufluss){
        double h1=vol/b/l;
        if(h1>minh&&h1<maxh) {
            vol += zufluss;
            h = vol / b / l;
            return true;
        }
        else{
            return false;
        }
    }

}
