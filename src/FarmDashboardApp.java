import javafx.application.Application;
import javafx.stage.Stage;
import view.DashboardView;

public class FarmDashboardApp extends Application {
	@Override
	public void start(Stage primaryStage) {
		DashboardView dashboard = new DashboardView(); // Create an instance of FarmDashboard
		primaryStage.setTitle("Farm Dashboard");
		primaryStage.setScene(dashboard.createScene()); // Set the scene from FarmDashboard
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}