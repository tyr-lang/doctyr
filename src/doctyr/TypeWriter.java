package doctyr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import tyr.tooling.sdk.ast.OGFile;
import tyr.tooling.sdk.ast.TypeDef;

public class TypeWriter {

   public static final File base = new File("docs", "types");

   public TypeWriter(OGFile sg) {
      for (var t : sg.TypeDefs)
         write(t);
   }

   private void write(TypeDef t) {
      var f = docFile(t);
      f.getParentFile().mkdirs();
      try (var out = new FileWriter(f)) {
         out.write("#description of type " + name(t) + "\n more text");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static File docFile(TypeDef t) {
      return new File(base, t.ID() + ".md");
   }

   public static String docFileRelativeName(TypeDef t) {
      return docFile(t).toString().substring(5);
   }

   public static String name(TypeDef t) {
      return t.getName();
   }

}
