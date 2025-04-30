package dev.luggers;

public class Speicherbecken {
	double l;
	double b;
	double h;
	double vol;
	double minh;
	double maxh;
	Speicherbecken nf;

	Speicherbecken(Speicherbecken nachfolger, double l, double b, double h, double minh, double maxh) {
		nf = nachfolger;
		this.l = l;
		this.b = b;
		this.h = h;
		this.vol = l * b * h;
		this.minh = minh;
		this.maxh = maxh;
	}

	public boolean berechnen(double fluss) {
		double h1 = (vol + fluss) / b / l;
		if (h1 >= minh) {
			if (h1 <= maxh) {
				vol += fluss;
				h = vol / b / l;
				return true;
			} else {
				if (nf != null) {
					nf.ueberfluss(fluss);
				}
				return true;
			}
		} else {
			return false;
		}
	}

	public void ueberfluss(double fluss) {
		double h1 = (vol + fluss) / b / l;
		if (h1 <= maxh) {
			vol += fluss;
			h = vol / b / l;
		} else {
			if (nf != null) {
				nf.ueberfluss(fluss);
			}
		}
	}
}
