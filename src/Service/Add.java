package Service;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.FarmItem;
import model.Item;
import model.ItemContainer;

public class Add extends Service {
	private Draw draw;

	public Add(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea) {
		super(treeView, rootItem, visualizationArea);
		draw = new Draw(treeView, rootItem, visualizationArea);
	}

	public void addItem() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof ItemContainer)) {
			return;
		}

		Dialog<FarmItem> dialog = new Dialog<>();
		dialog.setTitle("Add Item");
		dialog.setHeaderText("Enter item details");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField nameField = new TextField();
		nameField.setPromptText("Item Name");

		TextField locationXField = new TextField();
		locationXField.setPromptText("Location X");

		TextField locationYField = new TextField();
		locationYField.setPromptText("Location Y");

		TextField lengthField = new TextField();
		lengthField.setPromptText("Length");

		TextField widthField = new TextField();
		widthField.setPromptText("Width");

		TextField heightField = new TextField();
		heightField.setPromptText("Height");

		TextField priceField = new TextField();
		priceField.setPromptText("Price");

		grid.add(new Label("Name:"), 0, 0);
		grid.add(nameField, 1, 0);
		grid.add(new Label("Location X:"), 0, 1);
		grid.add(locationXField, 1, 1);
		grid.add(new Label("Location Y:"), 0, 2);
		grid.add(locationYField, 1, 2);
		grid.add(new Label("Length:"), 0, 3);
		grid.add(lengthField, 1, 3);
		grid.add(new Label("Width:"), 0, 4);
		grid.add(widthField, 1, 4);
		grid.add(new Label("Height:"), 0, 5);
		grid.add(heightField, 1, 5);
		grid.add(new Label("Price:"), 0, 6);
		grid.add(priceField, 1, 6);

		dialog.getDialogPane().setContent(grid);

		ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addButtonType) {
				String name = nameField.getText();
				double locationX = Double.parseDouble(locationXField.getText());
				double locationY = Double.parseDouble(locationYField.getText());
				double length = Double.parseDouble(lengthField.getText());
				double width = Double.parseDouble(widthField.getText());
				double height = Double.parseDouble(heightField.getText());
				double price = Double.parseDouble(priceField.getText());
				return new Item(name, locationX, locationY, length, width, height, price);
			}
			return null;
		});

		dialog.showAndWait().ifPresent(item -> {
			TreeItem<FarmItem> itemNode = new TreeItem<>(item);
			TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
			if (selectionItem == null) {
				rootItem.getChildren().add(itemNode);
				draw.drawFarmItem(item);
			} else {
				ItemContainer ic = (ItemContainer) selectionItem.getValue();
				ArrayList<FarmItem> icl = ic.getItemList();
				icl.add(item);
				ic.setItemList(icl);
				selectionItem.getChildren().add(itemNode);
				draw.drawFarmItem(item);
			}
		});
	}

	public void addItemContainer() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (selectedItem != null && selectedItem.getValue() instanceof Item) {
			return;
		}

		Dialog<FarmItem> dialog = new Dialog<>();
		dialog.setTitle("Add Item Container");
		dialog.setHeaderText("Enter item container details");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField nameField = new TextField();
		nameField.setPromptText("Item Container Name");

		TextField locationXField = new TextField();
		locationXField.setPromptText("Location X");

		TextField locationYField = new TextField();
		locationYField.setPromptText("Location Y");

		TextField lengthField = new TextField();
		lengthField.setPromptText("Length");

		TextField widthField = new TextField();
		widthField.setPromptText("Width");

		TextField heightField = new TextField();
		heightField.setPromptText("Height");

		TextField priceField = new TextField();
		priceField.setPromptText("Price");

		grid.add(new Label("Name:"), 0, 0);
		grid.add(nameField, 1, 0);
		grid.add(new Label("Location X:"), 0, 1);
		grid.add(locationXField, 1, 1);
		grid.add(new Label("Location Y:"), 0, 2);
		grid.add(locationYField, 1, 2);
		grid.add(new Label("Length:"), 0, 3);
		grid.add(lengthField, 1, 3);
		grid.add(new Label("Width:"), 0, 4);
		grid.add(widthField, 1, 4);
		grid.add(new Label("Height:"), 0, 5);
		grid.add(heightField, 1, 5);
		grid.add(new Label("Price:"), 0, 6);
		grid.add(priceField, 1, 6);

		dialog.getDialogPane().setContent(grid);

		ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addButtonType) {
				String name = nameField.getText();
				double locationX = Double.parseDouble(locationXField.getText());
				double locationY = Double.parseDouble(locationYField.getText());
				double length = Double.parseDouble(lengthField.getText());
				double width = Double.parseDouble(widthField.getText());
				double height = Double.parseDouble(heightField.getText());
				double price = Double.parseDouble(priceField.getText());
				return new ItemContainer(name, locationX, locationY, length, width, height, price);
			}
			return null;
		});

		dialog.showAndWait().ifPresent(item -> {
			TreeItem<FarmItem> itemNode = new TreeItem<>(item);
			TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
			System.out.println(selectedItem);
			if (selectionItem == null) {
				rootItem.getChildren().add(itemNode);
				draw.drawFarmItem(item);
			} else {
				selectionItem.getChildren().add(itemNode);
				draw.drawFarmItem(item);
			}
		});
	}

}
