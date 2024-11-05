package view;

import Service.Add;
import Service.ChangeDimensions;
import Service.ChangeLocation;
import Service.ChangePrice;
import Service.Delete;
import Service.Rename;
import controller.DashboardController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.FarmItem;
import model.ItemContainer;

public class DashboardView {
	private TreeView<FarmItem> treeView;
	private TreeItem<FarmItem> rootItem;
	private DashboardController dashboardController;
	private Pane visualizationArea;

	public DashboardView() {
		rootItem = new TreeItem<>(new ItemContainer("Root", 0, 0, 0, 0, 0, 0));
		rootItem.setExpanded(true);
		treeView = new TreeView<>(rootItem);

		visualizationArea = new Pane();
		visualizationArea.setPrefSize(800, 600);
		visualizationArea.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: lightgray;");
		visualizationArea.getChildren().clear();

		dashboardController = new DashboardController(treeView, rootItem, visualizationArea);
	}

	public Scene createScene() {

		this.treeView.setStyle("-fx-border-color: grey; -fx-border-width: 2px;");

		ScrollPane scrollPane = new ScrollPane(treeView);
		scrollPane.setPrefSize(200, 200);
		scrollPane.setFitToWidth(true);

		Label titleLabel = new Label("Farm Dashboard");
		titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

		HBox titleBox = new HBox(titleLabel);
		titleBox.setAlignment(Pos.CENTER);

		HBox mainPanel = new HBox(10);

		VBox leftPanel = new VBox(10);
		leftPanel.setStyle(
				"-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-background-color: white;");

		VBox rightPanel = new VBox(20);
		rightPanel.setStyle(
				"-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-background-color: white;");

		Label itemCommandsLabel = new Label("Commands on Items");
		Button itemRename = new Button("Rename");
		itemRename.setMaxWidth(Double.MAX_VALUE);
		itemRename.setOnAction(e -> new Rename(treeView, rootItem, visualizationArea).renameItem());

		Button itemChangeLocation = new Button("Change Location");
		itemChangeLocation.setMaxWidth(Double.MAX_VALUE);
		itemChangeLocation
				.setOnAction(e -> new ChangeLocation(treeView, rootItem, visualizationArea).changeItemLocation());

		Button itemChangeDimensions = new Button("Change Dimensions");
		itemChangeDimensions.setMaxWidth(Double.MAX_VALUE);
		itemChangeDimensions
				.setOnAction(e -> new ChangeDimensions(treeView, rootItem, visualizationArea).changeItemDimensions());

		Button itemDelete = new Button("Delete");
		itemDelete.setMaxWidth(Double.MAX_VALUE);
		itemDelete.setOnAction(e -> new Delete(treeView, rootItem, visualizationArea).deleteItem());

		Button itemChangePrice = new Button("Change Price");
		itemChangePrice.setMaxWidth(Double.MAX_VALUE);
		itemChangePrice.setOnAction(e -> new ChangePrice(treeView, rootItem, visualizationArea).changeItemPrice());

		VBox itemCommands = new VBox();
		itemCommands.getChildren().addAll(itemCommandsLabel, itemRename, itemChangeLocation, itemChangeDimensions,
				itemDelete, itemChangePrice);
		itemCommands.setStyle(
				"-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-background-color: white;");

		Label itemContainerCommandsLabel = new Label("Commands on Item Containers");
		Button itemContainerRename = new Button("Rename");
		itemContainerRename.setMaxWidth(Double.MAX_VALUE);
		itemContainerRename.setOnAction(e -> new Rename(treeView, rootItem, visualizationArea).renameItemContainer());

		Button itemContainerChangeLocation = new Button("Change Location");
		itemContainerChangeLocation.setMaxWidth(Double.MAX_VALUE);
		itemContainerChangeLocation.setOnAction(
				e -> new ChangeLocation(treeView, rootItem, visualizationArea).changeItemContainerLocation());

		Button itemContainerChangeDimensions = new Button("Change Dimensions");
		itemContainerChangeDimensions.setMaxWidth(Double.MAX_VALUE);
		itemContainerChangeDimensions.setOnAction(
				e -> new ChangeDimensions(treeView, rootItem, visualizationArea).changeItemContainerDimensions());

		Button itemContainerDelete = new Button("Delete");
		itemContainerDelete.setMaxWidth(Double.MAX_VALUE);
		itemContainerDelete.setOnAction(e -> new Delete(treeView, rootItem, visualizationArea).deleteItemContainer());

		Button itemContainerChangePrice = new Button("Change Price");
		itemContainerChangePrice.setMaxWidth(Double.MAX_VALUE);
		itemContainerChangePrice
				.setOnAction(e -> new ChangePrice(treeView, rootItem, visualizationArea).changeItemContainerPrice());

		Button addItem = new Button("Add Item");
		addItem.setMaxWidth(Double.MAX_VALUE);
		addItem.setOnAction(e -> new Add(treeView, rootItem, visualizationArea).addItem());

		Button addItemContainer = new Button("Add Item Container");
		addItemContainer.setMaxWidth(Double.MAX_VALUE);
		addItemContainer.setOnAction(e -> new Add(treeView, rootItem, visualizationArea).addItemContainer());

		VBox itemContainerCommands = new VBox();
		itemContainerCommands.getChildren().addAll(itemContainerCommandsLabel, itemContainerRename,
				itemContainerChangeLocation, itemContainerChangeDimensions, itemContainerDelete,
				itemContainerChangePrice, addItem, addItemContainer);
		itemContainerCommands.setStyle(
				"-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-background-color: white;");

		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton radioButton1 = new RadioButton("Visit Item/Item Container");
		RadioButton radioButton2 = new RadioButton("Scan Farm");
		radioButton1.setToggleGroup(toggleGroup);
		radioButton2.setToggleGroup(toggleGroup);

		toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			// Check if the selected toggle is the visitItemRadioButton
			if (newValue == radioButton1) {
				// Call the visit item method or handle the selection
				dashboardController.handleVisitItem();
			}
		});

		VBox radioButtonRow = new VBox(5);
		radioButtonRow.getChildren().addAll(radioButton1, radioButton2);
		radioButtonRow.setStyle(
				"-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-background-color: white;");

		leftPanel.getChildren().addAll(scrollPane, itemCommands, itemContainerCommands);
		rightPanel.getChildren().addAll(visualizationArea, radioButtonRow);
		mainPanel.getChildren().addAll(leftPanel, rightPanel);
		mainPanel.setPadding(new Insets(0, 10, 0, 10));

		// Set up the layout
		VBox layout = new VBox(10);
		layout.getChildren().addAll(titleBox, mainPanel);
		return new Scene(layout, 1100, 750);
	}
}
