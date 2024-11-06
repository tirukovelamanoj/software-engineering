package controller;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.FarmItem;

/**
 * Author: Manoj Tirukovela
 */

public class DashboardController {
	private final TreeView<FarmItem> treeView;
	private TreeItem<FarmItem> rootItem;
	private Pane visualizationArea;
	private ImageView drone;

	public DashboardController(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea,
			ImageView drone) {
		this.treeView = treeView;
		this.rootItem = rootItem;
		this.visualizationArea = visualizationArea;
		this.drone = drone;
	}

	public void handleVisitItem() {
		FarmItem selectedItem = treeView.getSelectionModel().getSelectedItem().getValue();
		if (selectedItem != null) {
			visitItem(selectedItem);
		}
	}

	public void handleScanFarm() {
		FarmItem selectedItem = treeView.getSelectionModel().getSelectedItem().getValue();
		if (selectedItem != null) {
			scanFarm(selectedItem);
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

	private void scanFarm(FarmItem item) {
		double itemLocationX = item.getLocationX();
		double itemLocationY = item.getLocationY();
		double width = item.getWidth();
		double height = item.getLength();

		double x = drone.getX();
		double y = drone.getY();
		double x1 = itemLocationX + width;
		double y1 = itemLocationY;
		double x2 = x1;
		double y2 = itemLocationY + height;
		double x3 = itemLocationX;
		double y3 = y2;

		Path path = new Path();
		path.getElements().add(new MoveTo(x + drone.getFitWidth()/2, y + drone.getFitHeight()/2));
		path.getElements().add(new LineTo(itemLocationX, itemLocationY));
		path.getElements().add(new LineTo(x1, y1));
		path.getElements().add(new LineTo(x2, y2));
		path.getElements().add(new LineTo(x3, y3));
		path.getElements().add(new LineTo(itemLocationX, itemLocationY));
		path.getElements().add(new LineTo(x + drone.getFitWidth()/2, y + drone.getFitHeight()/2));

		PathTransition pathTransition = new PathTransition();
		pathTransition.setPath(path);
		pathTransition.setNode(drone);
		pathTransition.setRate(0.05);
		pathTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);

		pathTransition.setCycleCount(1);

		pathTransition.play();
	}
}
