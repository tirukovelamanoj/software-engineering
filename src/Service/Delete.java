package Service;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.FarmItem;
import model.Item;
import model.ItemContainer;

public class Delete extends Service {

	public Delete(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea, ImageView drone) {
		super(treeView, rootItem, visualizationArea, drone);
	}

	public void deleteItem() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof Item)) {
			return;
		}
		if (selectedItem != null && selectedItem.getParent() != null) {
			TreeItem<FarmItem> parent = selectedItem.getParent();
			deleteIt(selectedItem.getValue());
			parent.getChildren().remove(selectedItem);
			TreeItem<FarmItem> root = treeView.getRoot();
			treeView.setRoot(null);
			treeView.setRoot(root);
		}
	}

	private void deleteIt(FarmItem item) {
		visualizationArea.getChildren().removeAll(item.getAssociatedShapes());
		ItemContainer parentContainer = (ItemContainer) item.getParent();
		ArrayList<FarmItem> icl = parentContainer.getItemList();
		icl.remove(item);
		parentContainer.setItemList(icl);
	}

	public void deleteItemContainer() {
		TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if (!(selectedItem != null && selectedItem.getValue() instanceof ItemContainer)) {
			return;
		}
		if (selectedItem != null && selectedItem.getParent() != null) {
			TreeItem<FarmItem> parent = selectedItem.getParent();
			deleteItc(selectedItem.getValue());
			parent.getChildren().remove(selectedItem);
			TreeItem<FarmItem> root = treeView.getRoot();
			treeView.setRoot(null);
			treeView.setRoot(root);
			visualizationArea.getChildren().clear();
			for (FarmItem items : ((ItemContainer) rootItem.getValue()).getItemList()) {
				if (items instanceof Item) {
					new Draw(treeView, rootItem, visualizationArea, drone).drawFarmItem((Item) items);
				} else {
					new Draw(treeView, rootItem, visualizationArea, drone).drawFarmItemContainer(items);
				}
			}
		}
	}

	private void deleteItc(FarmItem itemContainer) {
		visualizationArea.getChildren().removeAll(itemContainer.getAssociatedShapes());
		ItemContainer parentContainer = (ItemContainer) itemContainer.getParent();
		ArrayList<FarmItem> icl = parentContainer.getItemList();
		icl.remove(itemContainer);
		parentContainer.setItemList(icl);
	}
}
