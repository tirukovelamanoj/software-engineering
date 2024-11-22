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

public class ChangePrice extends Service {

	public ChangePrice(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea,
			ImageView drone) {
		super(treeView, rootItem, visualizationArea, drone);
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

	private void changePrice(String it) {
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

}
