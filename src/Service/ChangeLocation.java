package Service;

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

public class ChangeLocation extends Service {

	public ChangeLocation(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea) {
		super(treeView, rootItem, visualizationArea);
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

	private void changeLocation(String it) {
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
			new Draw(treeView, rootItem, visualizationArea).redraw(rItem);
		});
	}
}