package doctyr;

import java.io.IOException;

import ogss.common.java.api.OGSSException;
import tyr.tooling.sdk.ast.OGFile;

public class Main {
   public static void main(String[] args) throws OGSSException, IOException {
      try (var sg = OGFile.open("test/tyr.lang.aast.sg")) {
         new PackageWriter(sg);
         new TypeWriter(sg);
         new IndexWriter(sg);
         runMKDocs();
      }
   }

   private static void runMKDocs() throws IOException {
      var pb = new ProcessBuilder("mkdocs", "build").inheritIO();
      var p = pb.start();
      try {
         p.waitFor();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
}
