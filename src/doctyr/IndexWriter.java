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
  --md-primary-fg-color:        #000033;
  --md-primary-fg-color--light: #000044;
  --md-primary-fg-color--dark:  #000020;
  --md-primary-bg-color:        #ffff00;
  --md-primary-bg-color--light: #ffff44;

  --md-accent-fg-color:                #aaaa00;
  --md-accent-fg-color--transparent:   #aaaa0044;
  --md-accent-bg-color:                #101020;
  --md-accent-bg-color--light:         #202040;

  --md-default-fg-color:               #000000;
  --md-default-fg-color--light:        #202010;
  --md-default-fg-color--lighter:      #404020;
  --md-default-fg-color--lightest:     #808040;
  --md-default-bg-color:               #ffffff;
  --md-default-bg-color--light:        #eeeeee;
  --md-default-bg-color--lighter:      #dddddd;
  --md-default-bg-color--lightest:     #cccccc;


  --md-code-fg-color:                  #080800;
  --md-code-bg-color:                  #dddddd;
  --md-code-hl-color:                  #404000;
  --md-code-hl-color--light:           #50501044;

  --md-code-hl-number-color:           hsla(0, 67%, 50%, 1);
  --md-code-hl-special-color:          hsla(340, 83%, 47%, 1);
  --md-code-hl-function-color:         hsla(291, 45%, 50%, 1);
  --md-code-hl-constant-color:         hsla(250, 63%, 60%, 1);
  --md-code-hl-keyword-color:          hsla(219, 54%, 51%, 1);
  --md-code-hl-string-color:           hsla(150, 63%, 30%, 1);
  --md-code-hl-name-color:             var(--md-code-fg-color);
  --md-code-hl-operator-color:         var(--md-default-fg-color--light);
  --md-code-hl-punctuation-color:      var(--md-default-fg-color--light);
  --md-code-hl-comment-color:          var(--md-default-fg-color--light);
  --md-code-hl-generic-color:          var(--md-default-fg-color--light);
  --md-code-hl-variable-color:         var(--md-default-fg-color--light);

  --md-typeset-color:                  var(--md-default-fg-color);
  --md-typeset-a-color:                var(--md-primary-fg-color--light);

  --md-typeset-kbd-color:              hsla(0, 0%, 98%, 1);
  --md-typeset-kbd-accent-color:       hsla(0, 100%, 100%, 1);
  --md-typeset-kbd-border-color:       hsla(0, 0%, 72%, 1);

  --md-typeset-mark-color:             #1a488e88;

  --md-admonition-fg-color:            var(--md-default-fg-color);
  --md-admonition-bg-color:            #fafaf0;

  --md-warning-fg-color:              hsla(0, 0%, 0%, 0.87);
  --md-warning-bg-color:              hsla(60, 100%, 80%, 1);
}
[data-md-color-scheme="tyr-dark"] {
  --md-primary-fg-color:               #092147;
  --md-primary-fg-color--light:        #4488ff;
  --md-primary-fg-color--dark:         #082055;
  --md-primary-bg-color:               #ffffff;
  --md-primary-bg-color--light:        #dddddd;

  --md-accent-fg-color:                #88abdc;
  --md-accent-fg-color--transparent:   #88abdc44;
  --md-accent-bg-color:                #092147;
  --md-accent-bg-color--light:         #09214744;

  --md-default-fg-color:               #ffffff;
  --md-default-fg-color--light:        #bbddff;
  --md-default-fg-color--lighter:      #aabbff;
  --md-default-fg-color--lightest:     #7788ff;
  --md-default-bg-color:               #202020;
  --md-default-bg-color--light:        #202040;
  --md-default-bg-color--lighter:      #404080;
  --md-default-bg-color--lightest:     #8080ff;

  --md-code-fg-color:                  #eeeeee;
  --md-code-bg-color:                  #262626;
  --md-code-hl-color:                  #1a488e;
  --md-code-hl-color--light:           #1a488e44;

  --md-code-hl-number-color:           hsla(0, 67%, 50%, 1);
  --md-code-hl-special-color:          hsla(340, 83%, 47%, 1);
  --md-code-hl-function-color:         hsla(291, 45%, 50%, 1);
  --md-code-hl-constant-color:         hsla(250, 63%, 60%, 1);
  --md-code-hl-keyword-color:          hsla(219, 54%, 51%, 1);
  --md-code-hl-string-color:           hsla(150, 63%, 30%, 1);
  --md-code-hl-name-color:             var(--md-code-fg-color);
  --md-code-hl-operator-color:         var(--md-default-fg-color--light);
  --md-code-hl-punctuation-color:      var(--md-default-fg-color--light);
  --md-code-hl-comment-color:          var(--md-default-fg-color--light);
  --md-code-hl-generic-color:          var(--md-default-fg-color--light);
  --md-code-hl-variable-color:         var(--md-default-fg-color--light);

  --md-typeset-color:                  var(--md-default-fg-color);
  --md-typeset-a-color:                var(--md-primary-fg-color--light);

  --md-typeset-kbd-color:              hsla(0, 0%, 98%, 1);
  --md-typeset-kbd-accent-color:       hsla(0, 100%, 100%, 1);
  --md-typeset-kbd-border-color:       hsla(0, 0%, 72%, 1);

  --md-typeset-mark-color:             #1a488e88;

  --md-admonition-fg-color:            var(--md-default-fg-color);
  --md-admonition-bg-color:            #000011;

  --md-warning-fg-color:              hsla(0, 0%, 0%, 0.87);
  --md-warning-bg-color:              hsla(60, 100%, 80%, 1);
}

[data-md-color-scheme="tyr-debug"] {
  --md-primary-fg-color:               #ffff00;
  --md-primary-fg-color--light:        #ffffaa;
  --md-primary-fg-color--dark:         #eeee00;
  --md-primary-bg-color:               #00ff00;
  --md-primary-bg-color--light:        #00ffaa;

  --md-accent-fg-color:                #0000ff;
  --md-accent-fg-color--transparent:   #0000ff44;
  --md-accent-bg-color:                #000088;
  --md-accent-bg-color--light:         #00008844;

  --md-default-fg-color:               #cccc88;
  --md-default-fg-color--light:        #dddd99;
  --md-default-fg-color--lighter:      #eeeeaa;
  --md-default-fg-color--lightest:     #ffffbb;
  --md-default-bg-color:               #222210;
  --md-default-bg-color--light:        #333318;
  --md-default-bg-color--lighter:      #444420;
  --md-default-bg-color--lightest:     #555528;

  --md-code-fg-color:                  #cc00cc;
  --md-code-bg-color:                  #100010;
  --md-code-hl-color:                  #ff8800;
  --md-code-hl-color--light:           #ff880044;

  --md-code-hl-number-color:           hsla(0, 67%, 50%, 1);
  --md-code-hl-special-color:          hsla(340, 83%, 47%, 1);
  --md-code-hl-function-color:         hsla(291, 45%, 50%, 1);
  --md-code-hl-constant-color:         hsla(250, 63%, 60%, 1);
  --md-code-hl-keyword-color:          hsla(219, 54%, 51%, 1);
  --md-code-hl-string-color:           hsla(150, 63%, 30%, 1);
  --md-code-hl-name-color:             var(--md-code-fg-color);
  --md-code-hl-operator-color:         var(--md-default-fg-color--light);
  --md-code-hl-punctuation-color:      var(--md-default-fg-color--light);
  --md-code-hl-comment-color:          var(--md-default-fg-color--light);
  --md-code-hl-generic-color:          var(--md-default-fg-color--light);
  --md-code-hl-variable-color:         var(--md-default-fg-color--light);

  --md-typeset-color:                  var(--md-default-fg-color);
  --md-typeset-a-color:                var(--md-primary-fg-color--light);

  --md-typeset-kbd-color:              hsla(0, 0%, 98%, 1);
  --md-typeset-kbd-accent-color:       hsla(0, 100%, 100%, 1);
  --md-typeset-kbd-border-color:       hsla(0, 0%, 72%, 1);

  --md-typeset-mark-color:             #1a488e88;

  --md-admonition-fg-color:            var(--md-default-fg-color);
  --md-admonition-bg-color:            var(--md-default-bg-color);

  --md-warning-fg-color:              hsla(0, 0%, 0%, 0.87);
  --md-warning-bg-color:              hsla(60, 100%, 80%, 1);
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
markdown_extensions:
  - admonition
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
			out.write("  - Package: index.md\n");

			out.write("  - Types:\n");
			for (var t : sg.TypeDefs)
				out.write("    - \"" + TypeWriter.name(t) + "\": \"" + TypeWriter.docFileRelativeName(t) + "\"\n");

			out.write("  - Tags: tags.md\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
