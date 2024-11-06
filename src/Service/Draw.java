package Service;

import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.FarmItem;
import model.Item;

/**
 * Author: Manoj Tirukovela
 */

public class Draw extends Service {

	public Draw(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea, ImageView drone) {
		super(treeView, rootItem, visualizationArea, drone);
	}

	public void drawFarmItem(FarmItem item) {
		if(item instanceof Item) {
			Item i = (Item) item;
			if(i.checkDrone()) {
				drawDrone(i);
				return;
			}
		}
		Rectangle rectangle = new Rectangle(item.getLocationX(), item.getLocationY(), item.getWidth(),
				item.getLength());
		rectangle.setStroke(Color.BLUE);
		rectangle.setFill(Color.TRANSPARENT);

		Line diagonal1 = new Line(item.getLocationX(), item.getLocationY(), item.getLocationX() + item.getWidth(),
				item.getLocationY() + item.getLength());
		Line diagonal2 = new Line(item.getLocationX() + item.getWidth(), item.getLocationY(), item.getLocationX(),
				item.getLocationY() + item.getLength());
		diagonal1.setStroke(Color.BLUE);
		diagonal2.setStroke(Color.BLUE);

		Text itemDetails = new Text(item.getLocationX() + item.getWidth() + 10, item.getLocationY() + 10,
				" " + item.getName());

		item.setAssociatedShapes(List.of(rectangle, diagonal1, diagonal2, itemDetails));

		visualizationArea.getChildren().addAll(rectangle, diagonal1, diagonal2, itemDetails);
	}

	public void redraw(FarmItem item) {
		visualizationArea.getChildren().removeAll(item.getAssociatedShapes());
		drawFarmItem(item);
	}

	public void drawDrone(Item item) {
		drone.setX(item.getLocationX());
		drone.setY(item.getLocationY());
		drone.setFitWidth(item.getWidth());
		drone.setFitHeight(item.getLength());
		item.setAssociatedShapes(List.of(drone));
		visualizationArea.getChildren().addAll(drone);
	}
}
