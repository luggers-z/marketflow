package dev.luggers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Pool {
	final double normalHeight;
	protected DoubleProperty height = new SimpleDoubleProperty();
	Pool next;
	private double length;
	private double width;
	protected double volume;
	private double minh;
	private double maxh;
	private double maxVolume;
	private double minVolume;

	Pool(Pool next, double length, double width, double h, double minh, double maxh, double nHeight) {
		this.next = next;
		this.length = length;
		this.width = width;
		this.normalHeight = nHeight;
		height.set(h);
		this.volume = length * width * h;
		this.minh = minh;
		this.maxh = maxh;
		maxVolume = this.length * width * maxh;
		minVolume = this.length * width * minh;
	}

	public void setNext(Pool next) {
		this.next = next;
	}

	public void processFlow(double inFlow) {
		if (volume < minVolume) {
			volume = minVolume;
		}
		if (isFull()) {
			if (next != null) {
				next.triggerSpillway(inFlow);
			}
		}
		double projectedVolume = volume + inFlow;
		if (projectedVolume < maxVolume) {
			volume = projectedVolume;
		} else {
			volume = maxVolume;
			if (next != null) {
				next.triggerSpillway(projectedVolume - maxVolume);
			}
		}
		height.set(volume / width / length);
	}

	public boolean isFull() {
		return volume >= maxVolume;
	}

	public boolean isEmptyif(double inFlow) {
		double projectedVolume = volume + inFlow;
		double projectedHeight = projectedVolume / length / width;
		if (projectedHeight <= minh) {
			volume = minh * width * length;
			return true;
		} else {
			return false;
		}
	}

	public void triggerSpillway(double inFlow) {
		if (next != null) {
			next.processFlow(inFlow);
		}
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getMinVolume() {
		this.minVolume = this.length * this.width * this.minh;
		return this.minVolume;
	}

	public double getMaxVolume() {
		this.maxVolume = this.length * this.width * this.maxh;
		return this.maxVolume;
	}

	public double getNormalHeight() {
		return normalHeight;
	}
}
