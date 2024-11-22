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

public class ChangeLocation extends Service {

	public ChangeLocation(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea,
			ImageView drone) {
		super(treeView, rootItem, visualizationArea, drone);
	}

	public void changeItemLocation() {
		TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectionItem != null && selectionItem.getValue() instanceof Item)) {
			return;
		}

		Dialog<FarmItem> dialog = new Dialog<>();
		dialog.setTitle("Change Location");
		dialog.setHeaderText("Change Item Location");

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
				return new Item("", locationX, locationY, 0, 0, 0, 0, null);
			}
			return null;
		});

		dialog.showAndWait().ifPresent(item -> {
			TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
			FarmItem rItem = selectedItem.getValue();

			FarmItem ic = rItem.getParent();
			if (!(item.getLocationX() + rItem.getWidth() < ic.getWidth()
					&& item.getLocationY() + rItem.getLength() < ic.getLength())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Item should be inside Item container area!");
				alert.setContentText("The item leaving the allowed area. Please adjust.");
				alert.showAndWait();
				return;
			}

			rItem.setLocationX(item.getLocationX());
			rItem.setLocationY(item.getLocationY());
			selectedItem.setValue(null);
			selectedItem.setValue(rItem);
			new Draw(treeView, rootItem, visualizationArea, drone).redraw(rItem);
		});

	}

	public void changeItemContainerLocation() {
		TreeItem<FarmItem> selectionItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectionItem != null && selectionItem.getValue() instanceof ItemContainer)) {
			return;
		}

		if (selectionItem == rootItem) {
			return;
		}

		Dialog<FarmItem> dialog = new Dialog<>();
		dialog.setTitle("Change Location");
		dialog.setHeaderText("Change Item Container Location");

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
				return new ItemContainer("", locationX, locationY, 0, 0, 0, 0, null);
			}
			return null;
		});

		dialog.showAndWait().ifPresent(item -> {
			TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
			FarmItem rItem = selectedItem.getValue();

			ItemContainer pic = (ItemContainer) selectedItem.getParent().getValue();
			System.out.println("loc " + pic);
			if (item.getLocationX() + rItem.getWidth() > pic.getWidth()
					|| item.getLocationY() + rItem.getLength() > pic.getLength()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Dimensions exceed Parent Container area!");
				alert.setContentText("The item container dimensions exceed the allowed area. Please adjust.");
				alert.showAndWait();
				return;
			}

			rItem.setLocationX(item.getLocationX());
			rItem.setLocationY(item.getLocationY());
			selectedItem.setValue(null);
			selectedItem.setValue(rItem);
			new Draw(treeView, rootItem, visualizationArea, drone).redraw(rItem);
		});
	}
}