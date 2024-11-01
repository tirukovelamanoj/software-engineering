package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.FarmItem;
import model.Item;
import model.ItemContainer;
import view.DashboardView;

public class DashboardController {
	private ObservableList<FarmItem> items;
	private final TreeView<FarmItem> treeView;
	private TreeItem<FarmItem> rootItem;
	private Pane visualizationArea;
	private ImageView drone;

	public DashboardController(DashboardView dashboardView, TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem,
			ObservableList<FarmItem> items, Pane visualizationArea) {
		this.treeView = treeView;
		this.items = items;
		this.rootItem = rootItem;
		this.visualizationArea = visualizationArea;
		drawCommandCenter();
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
			items.add(item);
			TreeItem<FarmItem> itemNode = new TreeItem<>(item);
			TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
			if (selectionItem == null) {
				rootItem.getChildren().add(itemNode);
				drawFarmItem(item);
			} else {
				ItemContainer ic = (ItemContainer) selectionItem.getValue();
				ArrayList<FarmItem> icl = ic.getItemList();
				icl.add(item);
				ic.setItemList(icl);
				selectionItem.getChildren().add(itemNode);
				drawFarmItem(item);
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
			items.add(item);
			TreeItem<FarmItem> itemNode = new TreeItem<>(item);
			TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
			System.out.println(selectedItem);
			if (selectionItem == null) {
				rootItem.getChildren().add(itemNode);
				drawFarmItem(item);
			} else {
				selectionItem.getChildren().add(itemNode);
				drawFarmItem(item);
			}
		});
	}

	public void renameItem() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof Item)) {
			return;
		}
		rename("Item");
	}

	public void renameItemContainer() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof ItemContainer)) {
			return;
		}
		rename("Item Container");
	}

	public void rename(String it) {
		TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
		if (selectionItem == null) {
			return;
		}

		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Rename");
		dialog.setHeaderText("Rename " + it);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField nameField = new TextField();
		nameField.setPromptText("Change name to");

		grid.add(new Label("Name:"), 0, 0);
		grid.add(nameField, 1, 0);

		dialog.getDialogPane().setContent(grid);

		ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addButtonType) {
				String name = nameField.getText();
				return name;
			}
			return null;
		});

		dialog.showAndWait().ifPresent(itemName -> {
			TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();

			if (selectedItem != null && itemName != null && !itemName.trim().isEmpty()) {
				FarmItem item = selectedItem.getValue();
				item.setName(itemName);
				selectedItem.setValue(null);
				selectedItem.setValue(item);
				redraw(item);
			}
		});
	}

	public void changeItemLocation() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof Item)) {
			return;
		}
		changeLocation("Item");
	}

	public void changeItemContainerLocation() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof ItemContainer)) {
			return;
		}
		changeLocation("Item Container");
	}

	public void changeLocation(String it) {
		TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
		if (selectionItem == null) {
			return;
		}

		Dialog<FarmItem> dialog = new Dialog<>();
		dialog.setTitle("Change Location");
		dialog.setHeaderText("Change " + it + " Location");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField locationXField = new TextField();
		locationXField.setPromptText("Location X");

		TextField locationYField = new TextField();
		locationYField.setPromptText("Location Y");

		grid.add(new Label("Location X:"), 0, 0);
		grid.add(locationXField, 1, 0);
		grid.add(new Label("Location Y:"), 0, 1);
		grid.add(locationYField, 1, 1);

		dialog.getDialogPane().setContent(grid);

		ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addButtonType) {
				double locationX = Double.parseDouble(locationXField.getText());
				double locationY = Double.parseDouble(locationYField.getText());
				return new Item("", locationX, locationY, 0, 0, 0, 0);
			}
			return null;
		});

		dialog.showAndWait().ifPresent(item -> {
			TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
			FarmItem rItem = selectedItem.getValue();
			rItem.setLocationX(item.getLocationX());
			rItem.setLocationY(item.getLocationY());
			selectedItem.setValue(null);
			selectedItem.setValue(rItem);
			redraw(rItem);
		});
	}

	public void changeItemPrice() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof Item)) {
			return;
		}
		changePrice("Item");
	}

	public void changeItemContainerPrice() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof ItemContainer)) {
			return;
		}
		changePrice("Item Container");
	}

	public void changePrice(String it) {
		TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
		if (selectionItem == null) {
			return;
		}

		Dialog<Double> dialog = new Dialog<>();
		dialog.setTitle("Change Price");
		dialog.setHeaderText("Change " + it + " Price");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField priceField = new TextField();
		priceField.setPromptText("Change price to");

		grid.add(new Label("Price:"), 0, 0);
		grid.add(priceField, 1, 0);

		dialog.getDialogPane().setContent(grid);

		ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addButtonType) {
				double price = Double.parseDouble(priceField.getText());
				return price;
			}
			return null;
		});

		dialog.showAndWait().ifPresent(price -> {
			TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
			FarmItem item = selectedItem.getValue();
			item.setPrice(price);
			selectedItem.setValue(null);
			selectedItem.setValue(item);

		});
	}

	public void changeItemDimensions() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof Item)) {
			return;
		}
		changeDimensions("Item");
	}

	public void changeItemContainerDimensions() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof ItemContainer)) {
			return;
		}
		changeDimensions("Item Container");
	}

	public void changeDimensions(String it) {
		TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
		if (selectionItem == null) {
			return;
		}

		Dialog<FarmItem> dialog = new Dialog<>();
		dialog.setTitle("Change Dimensions");
		dialog.setHeaderText("Change " + it + " Dimensions");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField lengthField = new TextField();
		lengthField.setPromptText("Length");

		TextField widthField = new TextField();
		widthField.setPromptText("Width");

		TextField heightField = new TextField();
		heightField.setPromptText("Height");

		grid.add(new Label("Length:"), 0, 0);
		grid.add(lengthField, 1, 0);
		grid.add(new Label("Width:"), 0, 1);
		grid.add(widthField, 1, 1);
		grid.add(new Label("Height:"), 0, 2);
		grid.add(heightField, 1, 2);

		dialog.getDialogPane().setContent(grid);

		ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addButtonType) {
				double length = Double.parseDouble(lengthField.getText());
				double width = Double.parseDouble(widthField.getText());
				double height = Double.parseDouble(heightField.getText());
				return new Item("", 0, 0, length, width, height, 0);
			}
			return null;
		});

		dialog.showAndWait().ifPresent(item -> {
			TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
			FarmItem rItem = selectedItem.getValue();
			rItem.setLength(item.getLength());
			rItem.setWidth(item.getWidth());
			rItem.setHeight(item.getHeight());
			selectedItem.setValue(null);
			selectedItem.setValue(rItem);
			redraw(rItem);
		});
	}

	public void deleteItem() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof Item)) {
			return;
		}
		if (selectedItem != null && selectedItem.getParent() != null) {
			TreeItem<FarmItem> parent = selectedItem.getParent();
			visualizationArea.getChildren().removeAll(selectedItem.getValue().getAssociatedShapes());
			parent.getChildren().remove(selectedItem);
			TreeItem<FarmItem> root = treeView.getRoot();
			treeView.setRoot(null);
			treeView.setRoot(root);
		}
	}

	public void deleteItemContainer() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof ItemContainer)) {
			return;
		}
		if (selectedItem != null && selectedItem.getParent() != null) {
			TreeItem<FarmItem> parent = selectedItem.getParent();
			visualizationArea.getChildren().removeAll(selectedItem.getValue().getAssociatedShapes());
			for(FarmItem item: ((ItemContainer) selectedItem.getValue()).getItemList()) {
				visualizationArea.getChildren().removeAll(item.getAssociatedShapes());
			}
			parent.getChildren().remove(selectedItem);
			TreeItem<FarmItem> root = treeView.getRoot();
			treeView.setRoot(null);
			treeView.setRoot(root);
		}
	}

	public void drawFarmItem(FarmItem item) {
		Rectangle rectangle = new Rectangle(item.getLocationX(), item.getLocationY(), item.getWidth(),
				item.getLength());
		rectangle.setStroke(Color.BLUE);
		rectangle.setFill(Color.TRANSPARENT);

		Line diagonal1 = new Line(item.getLocationX(), item.getLocationY(), item.getLocationX() + item.getWidth(),
				item.getLocationY() + item.getLength());
		Line diagonal2 = new Line(item.getLocationX() + item.getWidth(), item.getLocationY(), item.getLocationX(),
				item.getLocationY() + item.getLength());
		diagonal1.setStroke(Color.BLUE);
		diagonal2.setStroke(Color.BLUE);

		Text itemDetails = new Text(item.getLocationX() + item.getWidth() + 10, item.getLocationY() + 10,
				" " + item.getName());

		item.setAssociatedShapes(List.of(rectangle, diagonal1, diagonal2, itemDetails));

		visualizationArea.getChildren().addAll(rectangle, diagonal1, diagonal2, itemDetails);
	}

	public void redraw(FarmItem item) {
		visualizationArea.getChildren().removeAll(item.getAssociatedShapes());
		drawFarmItem(item);
	}
	
	public void drawCommandCenter() {
		drawFarmItem(new Item("CommandCenter", 20, 20, 100, 200, 0, 0));
		Image image = new Image("drone.png");
        drone = new ImageView(image);
        drone.setX(80);
        drone.setY(35);
        visualizationArea.getChildren().add(drone);
	}
	
	public void handleVisitItem() {
        FarmItem selectedItem = treeView.getSelectionModel().getSelectedItem().getValue();
        if (selectedItem != null) {
            visitItem(selectedItem);
        }
    }

    private void visitItem(FarmItem item) {
        double itemLocationX = item.getLocationX();
        double itemLocationY = item.getLocationY();

        TranslateTransition flyToItem = new TranslateTransition(Duration.seconds(4), drone);
        flyToItem.setToX(itemLocationX - drone.getX());
        flyToItem.setToY(itemLocationY - drone.getY());

        TranslateTransition flyBack = new TranslateTransition(Duration.seconds(4), drone);
        flyBack.setToX(0);
        flyBack.setToY(0);

        flyToItem.setOnFinished(event -> flyBack.play());
        flyToItem.play();
    }
}
