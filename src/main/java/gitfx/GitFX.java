package gitfx;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GitFX extends Application {

	@SuppressFBWarnings(value = "BC_UNCONFIRMED_CAST_OF_RETURN_VALUE", justification = "FileRepositoryBuilder uses generics which spotbugs cant know")
	private static String jgit() throws IOException {
		try (Repository repository = new FileRepositoryBuilder()
				.setMustExist(true)
				.readEnvironment()
				.findGitDir(new File("/Users/aokeeffe/.dotfiles"))
				.build();
				RevWalk revWalk = new RevWalk(repository);
				TreeWalk treeWalk = new TreeWalk(repository)) {
			ObjectId headId = repository.resolve(Constants.HEAD);
			RevCommit headCommit = revWalk.parseCommit(headId);
			return headCommit.getShortMessage();
		}
	}

	@Override
	public void start(Stage stage) {
		String javaVersion = System.getProperty("java.version");
		String javafxVersion = System.getProperty("javafx.version");
		Label label = new Label("Hello, JavaFX " + javafxVersion
				+ ", running on Java " + javaVersion + ".");
		Label git = new Label("");
		try {
			git = new Label(jgit());
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageView imageView = new ImageView(new Image(
				GitFX.class.getResourceAsStream("/gitfx/openduke.png")));
		imageView.setFitHeight(200);
		imageView.setPreserveRatio(true);

		VBox root = new VBox(30, imageView, label, git);
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