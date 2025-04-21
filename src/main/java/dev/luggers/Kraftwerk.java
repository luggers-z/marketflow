package dev.luggers;

public class Kraftwerk {
    Kraftwerk nf;
    Speicherbecken pool;
    Kraftwerk(Kraftwerk nachfolger, Speicherbecken pool) {
        nf = nachfolger;
        this.pool = pool;
    }

}
