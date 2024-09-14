package doctyr;

import java.io.FileWriter;
import java.io.IOException;

import tyr.tooling.sdk.ast.OGFile;

public class IndexWriter {

   public IndexWriter(OGFile sg) {
      try (var out = new FileWriter("mkdocs.yml")) {
         out.write("site_name: \"" + String.join(".", sg.Packages.get().getName()) + "\"\n");
         out.write("docs_dir: \"docs\"\n");
         out.write("use_directory_urls: false\n");
         out.write("theme:\n" + "  name: mkdocs\n" + "  color_mode: dark\n");

         out.write("nav:\n");
         out.write("  - Home: index.md\n");

         out.write("  - types:\n");
         for (var t : sg.TypeDefs)
            out.write("    - \"" + TypeWriter.name(t) + "\": \"" + TypeWriter.docFileRelativeName(t) + "\"\n");

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
