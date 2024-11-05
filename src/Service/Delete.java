package Service;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import model.FarmItem;
import model.Item;
import model.ItemContainer;

/**
 * Author: Manoj Tirukovela
 */

public class Delete extends Service {

	public Delete(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea) {
		super(treeView, rootItem, visualizationArea);
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
			for (FarmItem item : ((ItemContainer) selectedItem.getValue()).getItemList()) {
				visualizationArea.getChildren().removeAll(item.getAssociatedShapes());
			}
			parent.getChildren().remove(selectedItem);
			TreeItem<FarmItem> root = treeView.getRoot();
			treeView.setRoot(null);
			treeView.setRoot(root);
		}
	}

}
