package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.PathTransition;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import model.FarmItem;
import model.Item;

/**
 * Author: Manoj Tirukovela
 */

public class DashboardController {
	private final TreeView<FarmItem> treeView;
	private ImageView drone;
	private TreeItem<FarmItem> rootItem;
	List<FarmItem> farmItems;

	public DashboardController(TreeView<FarmItem> treeView, ImageView drone, TreeItem<FarmItem> rootItem) {
		this.treeView = treeView;
		this.drone = drone;
		this.rootItem = rootItem;
	}

	public void handleVisitItem() {
		FarmItem selectedItem = treeView.getSelectionModel().getSelectedItem().getValue();
		if (selectedItem != null) {
			fly(selectedItem);
		}
	}

	public void handleScanFarm() {
		FarmItem selectedItem = treeView.getSelectionModel().getSelectedItem().getValue();
		if (selectedItem != null) {
			scanFarm();
		}
	}

	private List<FarmItem> getAllItemsFromTree(TreeItem<FarmItem> rootItem) {
		List<FarmItem> items = new ArrayList<>();

		if (rootItem == null)
			return items;

		for (TreeItem<FarmItem> child : rootItem.getChildren()) {
			collectItems(child, items);
		}

		return items;
	}

	private void collectItems(TreeItem<FarmItem> node, List<FarmItem> items) {
		FarmItem item = node.getValue();
		if (item != null) {
			if (item instanceof Item) {
				if (!((Item) item).checkDrone()) {
					items.add(item);
				}
			} else {
				items.add(item);
			}
		}
		for (TreeItem<FarmItem> child : node.getChildren()) {
			collectItems(child, items);
		}
	}

	private void scanFarm() {
		farmItems = getAllItemsFromTree(rootItem);
		visitItemsSequentially(0, (drone.getX() + drone.getFitWidth() / 2), (drone.getY() + drone.getFitHeight() / 2));
	}

	private void visitItemsSequentially(int index, double x, double y) {
		if (index >= farmItems.size()) {
			flyback(farmItems.get(index - 1));
			return;
		}

		FarmItem currentItem = farmItems.get(index);
		visitItem(currentItem, x, y, () -> {
			visitItemsSequentially(index + 1, currentItem.getLocationX(), currentItem.getLocationY());
		});
	}

	private void fly(FarmItem item) {
		visitItem(item, (drone.getX() + drone.getFitWidth() / 2), (drone.getY() + drone.getFitHeight() / 2),
				() -> flyback(item));
	}

	private void flyback(FarmItem item) {
		Path path = new Path();
		path.getElements().add(new MoveTo(item.getActualLocationX(), item.getActualLocationY()));
		path.getElements()
				.add(new LineTo(drone.getX() + drone.getFitWidth() / 2, drone.getY() + drone.getFitHeight() / 2));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setPath(path);
		pathTransition.setNode(drone);
		pathTransition.setRate(0.3);
		pathTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);
		pathTransition.setCycleCount(1);
		pathTransition.play();
	}

	private void visitItem(FarmItem item, double x, double y, Runnable onFinished) {
		double itemLocationX = item.getActualLocationX();
		double itemLocationY = item.getActualLocationY();
		double width = item.getWidth();
		double height = item.getLength();

		double x1 = itemLocationX + width;
		double y1 = itemLocationY;
		double x2 = x1;
		double y2 = itemLocationY + height;
		double x3 = itemLocationX;
		double y3 = y2;

		Path path = new Path();
		path.getElements().add(new MoveTo(x, y));
		path.getElements().add(new LineTo(itemLocationX, itemLocationY));
		path.getElements().add(new LineTo(x1, y1));
		path.getElements().add(new LineTo(x2, y2));
		path.getElements().add(new LineTo(x3, y3));
		path.getElements().add(new LineTo(itemLocationX, itemLocationY));
		path.getElements().add(new LineTo(x2, y2));
		path.getElements().add(new LineTo(x1, y1));
		path.getElements().add(new LineTo(x3, y3));
		path.getElements().add(new LineTo(itemLocationX, itemLocationY));

		PathTransition pathTransition = new PathTransition();
		pathTransition.setPath(path);
		pathTransition.setNode(drone);
		pathTransition.setRate(0.03);
		pathTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);

		pathTransition.setCycleCount(1);
		pathTransition.setOnFinished(event -> {
			if (onFinished != null) {
				onFinished.run();
			}
		});

		pathTransition.play();
	}
}
