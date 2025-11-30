package doctyr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import tyr.tooling.sdk.ast.DocComment;
import tyr.tooling.sdk.ast.OGFile;
import tyr.tooling.sdk.ast.PublicVisible;
import tyr.tooling.sdk.ast.Test;
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
         writeTags(t, out);

         out.write("# " + name(t) + "\n\n");

         writeComment(t.getDoc(), out);

         out.write("\n## Parents\n\n");
         out.write(DiagramMaker.superTypes(t));
         out.write("\n");

         writeMembers(t, out);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void writeMembers(TypeDef t, FileWriter out) throws IOException {
      if (t.getMembers().isEmpty())
         return;

      for (var m : t.getMembers()) {
         if (m instanceof Test)
            continue;

         out.write(String.format("## %s %s\n\n", Pretty.visit(m.getVisibility()), m.getName()));
         writeComment(m.getDoc(), out);
      }
   }

   private void writeComment(DocComment doc, FileWriter out) throws IOException {
      if (null == doc) {
         out.write("(undocumented entity)\n\n");
         return;
      }

      for (var p : doc.getDetails()) {
         out.write(String.join(" ", p.getWords()));
         out.write("\n\n");
      }

      for (var t : doc.getTags()) {
         if (null != t.getWords() && !t.getWords().isEmpty()) {
            var words = String.join(" ", t.getWords());
            out.write(String.format("!!! %s \"%s %s\"\n\n", boxKind(t.getName()), t.getName(), words));
         } else {
            out.write(String.format("!!! %s \"%s\"\n\n", boxKind(t.getName()), t.getName()));
         }
         for (var p : t.getContent()) {
            out.write("    " + String.join(" ", p.getWords()) + "\n");
         }
         out.write("\n");
      }
   }

   private String boxKind(String tag) {
      return switch (tag) {
      case "note" -> "note";
      case "author" -> "abstract";

      case "pre" -> "question";
      case "post", "inv" -> "success";

      case "param" -> "info";
      case "return" -> "tip";

      case "todo", "bug" -> "bug";

      default -> "quote";
      };
   }

   private void writeTags(TypeDef t, FileWriter out) throws IOException {
      out.write("---\n");
      out.write("tags:\n");

      if (t instanceof TypeDef)
         out.write("  - type\n");

      if (t.getVisibility() instanceof PublicVisible)
         out.write("  - public\n");

      out.write("---\n");
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
