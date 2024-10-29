package doctyr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import ogss.common.java.api.OGSSException;

public class Main {
	public static void main(String[] args) throws OGSSException, IOException {
		var out = new File("out");
		delete(out);
		out.mkdirs();

		var tmp = Files.createTempDirectory("doctyr").toFile();

		try (var s = new State("test/tyr.lang.aast.sg", tmp, out)) {
			s.prepare();
			s.generate();
		} finally {
			delete(tmp);
		}
	}

	private static void delete(File f) throws IOException {
		if (!f.exists())
			return;

		if (f.isFile()) {
			f.delete();
			return;
		}

		try (var walk = Files.walk(f.toPath())) {
			walk.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		}
	}
}
