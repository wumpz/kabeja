package org.kabeja.dxf;

import static java.nio.file.Files.newInputStream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.kabeja.DraftDocument;
import org.kabeja.dxf.parser.DXFParserBuilder;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;

public class Main {

  /*
   * For developing.
   */
  public static void main(String[] args) {

    Path dxf = Paths.get("C:\\Users\\Veronika\\Desktop\\dev.dxf");

    try (BufferedInputStream bis = new BufferedInputStream(newInputStream(dxf))) {
      Parser parser = DXFParserBuilder.createDefaultParser();
      Map<String, Object> properties = new HashMap<>();
      properties.put(DraftDocument.PROPERTY_ENCODING, "UTF-8");
      DraftDocument draftDocument = parser.parse(bis, properties);

      // do stuff...

    } catch (ParseException | NullPointerException | IOException e) {
      e.printStackTrace();
    }
  }
}
