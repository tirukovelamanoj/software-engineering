package model;

/**
 * Author: Manoj Tirukovela
 */

public class Item extends FarmItem {
	private boolean isDrone = false;

	public Item(String name, double locationX, double locationY, double length, double width, double height,
			double price, FarmItem parent) {
		super(name, locationX, locationY, length, width, height, price, parent);
	}

	public boolean checkDrone() {
		return isDrone;
	}

	public void setIsDrone(boolean isDrone) {
		this.isDrone = isDrone;
	}
}
