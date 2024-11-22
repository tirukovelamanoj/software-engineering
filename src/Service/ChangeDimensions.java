package Service;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.FarmItem;
import model.Item;
import model.ItemContainer;

public class ChangeDimensions extends Service {

	public ChangeDimensions(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea,
			ImageView drone) {
		super(treeView, rootItem, visualizationArea, drone);
	}

	public void changeItemDimensions() {
		TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectionItem != null && selectionItem.getValue() instanceof Item)) {
			return;
		}

		Dialog<FarmItem> dialog = new Dialog<>();
		dialog.setTitle("Change Dimensions");
		dialog.setHeaderText("Change Item Dimensions");

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
				return new Item("", 0, 0, length, width, height, 0, null);
			}
			return null;
		});

		dialog.showAndWait().ifPresent(item -> {
			TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
			FarmItem rItem = selectedItem.getValue();

			FarmItem ic = rItem.getParent();
			if (!(rItem.getLocationX() + item.getWidth() < ic.getWidth()
					&& rItem.getLocationY() + item.getLength() < ic.getLength())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Item should be inside Item container area!");
				alert.setContentText("The item leaving the allowed area. Please adjust.");
				alert.showAndWait();
				return;
			}

			rItem.setLength(item.getLength());
			rItem.setWidth(item.getWidth());
			rItem.setHeight(item.getHeight());
			selectedItem.setValue(null);
			selectedItem.setValue(rItem);
			new Draw(treeView, rootItem, visualizationArea, drone).redraw(rItem);
		});

	}

	public void changeItemContainerDimensions() {
		TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectionItem != null && selectionItem.getValue() instanceof ItemContainer)) {
			return;
		}

		if (selectionItem == rootItem) {
			return;
		}

		Dialog<FarmItem> dialog = new Dialog<>();
		dialog.setTitle("Change Dimensions");
		dialog.setHeaderText("Change Item Container Dimensions");

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
				return new Item("", 0, 0, length, width, height, 0, null);
			}
			return null;
		});

		dialog.showAndWait().ifPresent(item -> {
			TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
			FarmItem rItem = selectedItem.getValue();

			ItemContainer pic = (ItemContainer) selectedItem.getParent().getValue();
			if (rItem.getActualLocationX() + item.getWidth() > pic.getWidth()
					|| rItem.getActualLocationY() + item.getLength() > pic.getLength()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Dimensions exceed Parent Container area!");
				alert.setContentText("The item container dimensions exceed the allowed area. Please adjust.");
				alert.showAndWait();
				return;
			}

			rItem.setLength(item.getLength());
			rItem.setWidth(item.getWidth());
			rItem.setHeight(item.getHeight());
			selectedItem.setValue(null);
			selectedItem.setValue(rItem);
			new Draw(treeView, rootItem, visualizationArea, drone).redraw(rItem);
		});
	}

}
