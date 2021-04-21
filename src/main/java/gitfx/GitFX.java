package gitfx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GitFX extends Application {

	@Override
	public void start(Stage stage) {
		String javaVersion = System.getProperty("java.version");
		String javafxVersion = System.getProperty("javafx.version");
		Label label = new Label("Hello, JavaFX " + javafxVersion
				+ ", running on Java " + javaVersion + ".");

		ImageView imageView = new ImageView(new Image(
				GitFX.class.getResourceAsStream("/gitfx/openduke.png")));
		imageView.setFitHeight(200);
		imageView.setPreserveRatio(true);

		VBox root = new VBox(30, imageView, label);
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, 640, 480);
		scene.getStylesheets()
				.add(GitFX.class.getResource("styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}