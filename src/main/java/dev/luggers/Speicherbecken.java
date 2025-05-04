package dev.luggers;

public class Speicherbecken {
	double length;
	double width;
	double height;
	double volume;
	double minh;
	double maxh;
	double maxvolume;
	Speicherbecken next;

	Speicherbecken(Speicherbecken next, double length, double width, double height, double minh, double maxh) {
		this.next = next;
		this.length = length;
		this.width = width;
		this.height = height;
		this.volume = length * width * height;
		this.minh = minh;
		this.maxh = maxh;
		maxvolume= this.length*width*maxh;
	}

	Speicherbecken(){

	}

	public void processFlow(double inFlow) {
		if(isFull()){
			if (next != null) {
				next.triggerSpillway(inFlow);
			}
		}
		double projectedVolume= volume+inFlow;
		if(projectedVolume < maxvolume){
			volume = projectedVolume;
		}
		else{
			volume = maxvolume;
            if(next != null) {
				next.triggerSpillway(projectedVolume - maxvolume);
			}
		}
		height = volume / width / length;
	}
    public boolean isFull(){
        return volume == maxvolume;
	}
	public void triggerSpillway(double inFlow) {
		next.processFlow(inFlow);
	}
}
