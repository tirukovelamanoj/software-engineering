package model;

import java.util.ArrayList;

/**
 * Author: Manoj Tirukovela
 */

public class ItemContainer extends FarmItem {

	private ArrayList<FarmItem> ItemList = new ArrayList<>();

	public ItemContainer(String name, double locationX, double locationY, double length, double width, double height,
			double price) {
		super(name, locationX, locationY, length, width, height, price);
	}

	public ArrayList<FarmItem> getItemList() {
		return ItemList;
	}

	public void setItemList(ArrayList<FarmItem> itemList) {
		ItemList = itemList;
	}
}
