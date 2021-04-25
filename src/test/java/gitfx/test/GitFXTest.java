package gitfx.test;

import java.io.File;

import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GitFXTest {

	@Test
	void testNoGitRepo() throws Exception {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new FileRepositoryBuilder().setMustExist(true)
					.readEnvironment()
					.findGitDir(new File("/"))
					.build()
					.close();
		});
	}

	@Test
	void testGitRepo() throws Exception {
		new FileRepositoryBuilder().setMustExist(true)
				.readEnvironment()
				.findGitDir(new File("."))
				.build()
				.close();
	}

}
