package org.kabeja.dxf;

import org.kabeja.DraftDocument;
import org.kabeja.common.Layer;
import org.kabeja.dxf.parser.DXFParserBuilder;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by benjaminaaron on 30/12/2016.
 */
public class Dev {

	public static void main(String[] args) throws ParseException, IOException {

		InputStream is = new FileInputStream("samples/dxf/pumpkin_AutoCad2016.dxf");

		Parser parser = DXFParserBuilder.createDefaultParser();
		DraftDocument draftDocument = parser.parse(is, new HashMap<String, Object>());

		Collection<Layer> layers = draftDocument.getLayers();

		for (Layer layer : layers) {
			System.out.println(layer.getName());
		}

	}

}
