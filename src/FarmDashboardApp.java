import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.DashboardView;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FarmDashboardApp extends Application {

	private static FarmDashboardApp instance;
	private static FileLock lock;
	private static RandomAccessFile lockFileRAF;

	public FarmDashboardApp() {
	}

	public static FarmDashboardApp getInstance() {
		if (instance == null) {
			instance = new FarmDashboardApp();
		}
		return instance;
	}

	@Override
	public void start(Stage primaryStage) {
		DashboardView dashboardView = new DashboardView();
		Scene scene = dashboardView.createScene();

		// Configure the primary stage
		primaryStage.setTitle("Farm Dashboard");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		if (!acquireFileLock()) {
			System.err.println("Application is already running!");
			System.exit(1);
		}

		getInstance();
		launch(args);

		// Release the file lock when the application exits
		releaseFileLock();
	}

	private static boolean acquireFileLock() {
		try {
			File lockFile = new File("app.lock");
			lockFileRAF = new RandomAccessFile(lockFile, "rw");
			FileChannel channel = lockFileRAF.getChannel();
			lock = channel.tryLock();

			if (lock == null) {
				lockFileRAF.close();
				return false;
			}

			lockFile.deleteOnExit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void releaseFileLock() {
		try {
			if (lock != null) {
				lock.release();
			}
			if (lockFileRAF != null) {
				lockFileRAF.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
