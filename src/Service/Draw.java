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
import model.ItemContainer;

public class Draw extends Service {

	public Draw(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea, ImageView drone) {
		super(treeView, rootItem, visualizationArea, drone);
	}

	public void drawFarmItem(Item item) {
		if (item.checkDrone()) {
			drawDrone(item);
			return;
		}

		Rectangle rectangle = new Rectangle(item.getActualLocationX(), item.getActualLocationY(), item.getWidth(),
				item.getLength());
		rectangle.setStroke(Color.BLUE);
		rectangle.setFill(Color.TRANSPARENT);

		Line diagonal1 = new Line(item.getActualLocationX(), item.getActualLocationY(),
				item.getActualLocationX() + item.getWidth(), item.getActualLocationY() + item.getLength());
		Line diagonal2 = new Line(item.getActualLocationX() + item.getWidth(), item.getActualLocationY(),
				item.getActualLocationX(), item.getActualLocationY() + item.getLength());
		diagonal1.setStroke(Color.BLUE);
		diagonal2.setStroke(Color.BLUE);

		Text itemDetails = new Text(item.getActualLocationX() + item.getWidth() + 10, item.getActualLocationY() + 10,
				" " + item.getName());

		item.setAssociatedShapes(List.of(rectangle, diagonal1, diagonal2, itemDetails));

		visualizationArea.getChildren().addAll(rectangle, diagonal1, diagonal2, itemDetails);
	}

	public void drawFarmItemContainer(FarmItem item) {
		Rectangle rectangle = new Rectangle(item.getActualLocationX(), item.getActualLocationY(), item.getWidth(),
				item.getLength());
		rectangle.setStroke(Color.BLUE);
		rectangle.setFill(Color.TRANSPARENT);

		Line diagonal1 = new Line(item.getActualLocationX(), item.getActualLocationY(),
				item.getActualLocationX() + item.getWidth(), item.getActualLocationY() + item.getLength());
		Line diagonal2 = new Line(item.getActualLocationX() + item.getWidth(), item.getActualLocationY(),
				item.getActualLocationX(), item.getActualLocationY() + item.getLength());
		diagonal1.setStroke(Color.BLUE);
		diagonal2.setStroke(Color.BLUE);

		Text itemDetails = new Text(item.getActualLocationX() + item.getWidth() + 10, item.getActualLocationY() + 10,
				" " + item.getName());

		item.setAssociatedShapes(List.of(rectangle, diagonal1, diagonal2, itemDetails));

		visualizationArea.getChildren().addAll(rectangle, diagonal1, diagonal2, itemDetails);

		for (FarmItem items : ((ItemContainer) item).getItemList()) {
			redraw(items);
		}
	}

	public void redraw(FarmItem item) {
		visualizationArea.getChildren().removeAll(item.getAssociatedShapes());
		if (item instanceof Item) {
			drawFarmItem((Item) item);
		} else {
			drawFarmItemContainer(item);
		}
	}

	public void drawDrone(Item item) {
		drone.setX(item.getActualLocationX());
		drone.setY(item.getActualLocationY());
		drone.setFitWidth(item.getWidth());
		drone.setFitHeight(item.getLength());
		item.setAssociatedShapes(List.of(drone));
		visualizationArea.getChildren().addAll(drone);
	}
}
