package controller;

import Service.Draw;
import javafx.animation.TranslateTransition;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.FarmItem;
import model.Item;

public class DashboardController {
	private final TreeView<FarmItem> treeView;
	private TreeItem<FarmItem> rootItem;
	private Pane visualizationArea;
	private ImageView drone;

	public DashboardController(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea) {
		this.treeView = treeView;
		this.rootItem = rootItem;
		this.visualizationArea = visualizationArea;
		drawCommandCenter();
	}

	public void drawCommandCenter() {
		new Draw(treeView, rootItem, visualizationArea).drawFarmItem(new Item("CommandCenter", 20, 20, 100, 200, 0, 0));
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
