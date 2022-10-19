package gitfx;

import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Stream;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

public class GitFX extends Application {

	ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

	Repository repository;

	@SuppressFBWarnings(
			value = "BC_UNCONFIRMED_CAST_OF_RETURN_VALUE",
			justification = "FileRepositoryBuilder uses generics which spotbugs cant know"
	)
	private Stream<RevCommit> jgit(String path) throws IOException {
		File gitDir = Path.of(path).toAbsolutePath().normalize().toFile();
		repository = new FileRepositoryBuilder().setMustExist(true)
				.readEnvironment()
				.findGitDir(gitDir)
				.build();
		RevWalk revWalk = new RevWalk(repository);
		ObjectId headId = repository.resolve(Constants.HEAD);
		RevCommit headCommit = revWalk.parseCommit(headId);
		return Stream.iterate(headCommit, c -> c.getParentCount() > 0, c -> {
			try {
				return revWalk.parseCommit(c.getParent(0));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}).onClose(revWalk::close);
	}

	@Override
	public void start(Stage stage) {
		System.setProperty("prism.lcdtext", "false");
		String path;
		if (!getParameters().getUnnamed().isEmpty()) {
			path = getParameters().getUnnamed().get(0);
		} else {
			path = "";
		}

		Label git = new Label("...");

		VBox root = new VBox(30, git);
		root.getStyleClass().add(JMetroStyleClass.BACKGROUND);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 640, 480);

		JMetro jMetro = new JMetro(Style.DARK);
		jMetro.setScene(scene);

		stage.setScene(scene);
		stage.show();

		executor.execute(() -> {
			try {
				String jgit = jgit(path).limit(10)
						.map(RevCommit::getShortMessage)
						.collect(joining("\n"));
				Platform.runLater(() -> git.setText(jgit));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void stop() throws Exception {
		executor.shutdown();
		if (repository != null) {
			repository.close();
		}
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
