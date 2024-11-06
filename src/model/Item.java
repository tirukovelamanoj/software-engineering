package model;

/**
 * Author: Manoj Tirukovela
 */

public class Item extends FarmItem {
	private boolean isDrone = false;

	public Item(String name, double locationX, double locationY, double length, double width, double height,
			double price, boolean isDrone) {
		super(name, locationX, locationY, length, width, height, price);
		this.isDrone = isDrone;
	}

	public Item(String name, double locationX, double locationY, double length, double width, double height,
			double price) {
		super(name, locationX, locationY, length, width, height, price);
	}

	public boolean checkDrone() {
		return isDrone;
	}
}
