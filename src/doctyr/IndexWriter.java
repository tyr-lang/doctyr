package doctyr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
		
		// color scheme
		{
			var f = new File("docs/stylesheets/extra.css");
			f.getParentFile().mkdirs();
			try (var out = new FileWriter(f)) {
				out.write("""
[data-md-color-scheme="tyr"] {
  --md-primary-fg-color:        #092147;
  --md-primary-fg-color--light: #1a488e;
  --md-primary-fg-color--dark:  #00193e;
}
[data-md-color-scheme="tyr-dark"] {
  --md-primary-fg-color:        #092147;
  --md-primary-fg-color--light: #1a488e;
  --md-primary-fg-color--dark:  #00193e;

  --md-accent-fg-color:                rgba(0, 0, 1, 1);
  --md-accent-fg-color--transparent:   rgba(0, 0, 1, 0.1);
  --md-accent-bg-color:                rgba(0, 0, 0, 1);
  --md-accent-bg-color--light:         rgba(0, 0, 0, 0.7);
}
""");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// copy logo
		try {
			Files.copy(Paths.get("logo.png"), Paths.get("docs", "logo.png"), StandardCopyOption.REPLACE_EXISTING);
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
extra_css:
  - stylesheets/extra.css
theme:
  name: material
  logo: logo.png
  favicon: logo.png
  palette:
    # Palette toggle for dark mode
    - scheme: tyr-dark
      toggle:
        icon: material/brightness-4
        name: Switch to light mode
    # Palette toggle for light mode
    - scheme: tyr
      toggle:
        icon: material/brightness-7 
        name: Switch to dark mode
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
