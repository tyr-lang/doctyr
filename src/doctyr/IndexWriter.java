package doctyr;

import java.io.FileWriter;
import java.io.IOException;

import tyr.tooling.sdk.ast.OGFile;

public class IndexWriter {

	public IndexWriter(OGFile sg) {
		// touch tags to ensure it can be generated (whatever went wrong in python in
		// that regard)
		try (var out = new FileWriter("docs/tags.md")) {
			// empty file
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create the configuration holding the actual index amongst others
		try (var out = new FileWriter("mkdocs.yml")) {
			out.write("site_name: \"" + String.join(".", sg.Packages.get().getName()) + "\"\n");
			out.write("""
docs_dir: \"docs\"
plugins:
  - tags:
      tags_file: tags.md
  - offline
  - privacy
  - search
theme:
  name: material
""");

			out.write("nav:\n");
			out.write("  - Home: index.md\n");

			out.write("  - types:\n");
			for (var t : sg.TypeDefs)
				out.write("    - \"" + TypeWriter.name(t) + "\": \"" + TypeWriter.docFileRelativeName(t) + "\"\n");

			out.write("  - Tags: tags.md\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
