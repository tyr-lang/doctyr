package doctyr;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import ogss.common.java.api.Mode;
import ogss.common.java.api.OGSSException;
import tyr.tooling.sdk.ast.OGFile;

/**
 * Holds the state of translation and abstracts tmp, in and out directories.
 */
public class State implements Closeable {

   private final OGFile sg;
   private final File tmp;
   private final File out;

   public State(String inPath, File tmp, File out) throws OGSSException, IOException {
      out.mkdirs();
      sg = OGFile.open(inPath, Mode.Read, Mode.ReadOnly);
      this.tmp = tmp;
      this.out = out;
   }

   @Override
   public void close() throws IOException {
      sg.close();
   }

   public void prepare() {
      new PackageWriter(sg);
      new TypeWriter(sg);
      new IndexWriter(sg);
   }

   public void generate() throws IOException {
      var pb = new ProcessBuilder("mkdocs", "build").inheritIO();
      var p = pb.start();
      try {
         p.waitFor();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

}
