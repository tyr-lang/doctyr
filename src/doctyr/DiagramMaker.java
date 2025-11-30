package doctyr;

import tyr.tooling.sdk.ast.Node;
import tyr.tooling.sdk.ast.TypeDef;

/**
 * Creates the text for some relationship diagrams.
 *
 * @author Timm Felden
 */
public class DiagramMaker {

   public static String superTypes(TypeDef t) {
      var sb = new StringBuilder();
      sb.append("``` mermaid\n");
      sb.append("flowchart TD\n");

      for(var p : t.getExtensions()) {
         sb.append(String.format("  %s[%s] --> %s\n", node(p), Pretty.visit(p), node(t)));
      }

      // define the type itself
      sb.append(String.format("  %s(%s)\n", node(t), t.getName()));

      sb.append("```\n");

      return sb.toString();
   }

   private static String node(Node n) {
      return "n" + n.ID();
   }

}
