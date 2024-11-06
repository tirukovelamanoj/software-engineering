package Service;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.FarmItem;

/**
 * Author: Manoj Tirukovela
 */

public abstract class Service {
	protected final TreeView<FarmItem> treeView;
	protected TreeItem<FarmItem> rootItem;
	protected Pane visualizationArea;
	protected ImageView drone;

	public Service(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea, ImageView drone) {
		this.treeView = treeView;
		this.rootItem = rootItem;
		this.visualizationArea = visualizationArea;
		this.drone = drone;
	}
}
