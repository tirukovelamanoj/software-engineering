package Service;

import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.FarmItem;

/**
 * Author: Manoj Tirukovela
 */

public class Draw extends Service {

	public Draw(TreeView<FarmItem> treeView, TreeItem<FarmItem> rootItem, Pane visualizationArea) {
		super(treeView, rootItem, visualizationArea);
	}

	public void drawFarmItem(FarmItem item) {
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
}
