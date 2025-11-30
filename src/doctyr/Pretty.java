package doctyr;

import tyr.tooling.sdk.ast.Expression;
import tyr.tooling.sdk.ast.OverrideVisible;
import tyr.tooling.sdk.ast.PackageVisible;
import tyr.tooling.sdk.ast.PublicVisible;
import tyr.tooling.sdk.ast.RestrictedVisible;
import tyr.tooling.sdk.ast.StaticAccess;
import tyr.tooling.sdk.ast.Visibility;

public class Pretty {
   public static String visit(Visibility vis) {
      return switch (vis) {
      case null -> "(null)";
      case PublicVisible v -> "public";
      case OverrideVisible v -> "override";
      case PackageVisible v -> "";
      case RestrictedVisible v -> {
         if (v.getIsProtected()) {
            if (v.getIsPrivate())
               yield "protected private";

            yield "protected";
         }
         yield "private";
      }
      default -> "???";
      };
   }

   public static String visit(Expression ex) {
      return switch (ex) {
      case StaticAccess e -> e.getName();
      default -> "???";
      };
   }
}
