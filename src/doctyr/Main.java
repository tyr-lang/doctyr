package doctyr;

import java.io.IOException;

import ogss.common.java.api.OGSSException;
import tyr.tooling.sdk.ast.OGFile;

public class Main {
   public static void main(String[] args) throws OGSSException, IOException {
      for(var t : OGFile.open("test/tyr.lang.aast.sg").TypeDefs) {
         System.out.println(t.getName());
      }
   }
}
