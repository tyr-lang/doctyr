package doctyr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import tyr.tooling.sdk.ast.OGFile;

public class PackageWriter {

   public PackageWriter(OGFile sg) {
      try (var out = new FileWriter(new File("docs", "index.md"))) {
         out.write("# " + String.join(".", sg.Packages.get().getName()) + "\n");
         out.write("more about this package\n");

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
