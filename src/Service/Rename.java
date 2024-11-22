package Service;

import javafx.geometry.Insets;
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

/**
 * Author: Manoj Tirukovela
 */

public class Rename extends Service {

	public Rename(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea, ImageView drone) {
		super(treeView, rootItem, visualizationArea, drone);
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
		if (selectedItem == rootItem) {
			return;
		}
		rename("Item Container");
	}

	private void rename(String it) {
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
				new Draw(treeView, rootItem, visualizationArea, drone).redraw(item);
			}
		});
	}

}
