/*******************************************************************************
 * Copyright 2010 Simon Mieth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.kabeja.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public class ConfigHelper {
  public static final String JAVA_15_SAX_DRIVER =
      "com.sun.org.apache.xerces.internal.parsers.SAXParser";

  public static String getSAXSDDriver() {
    String parser = JAVA_15_SAX_DRIVER;

    try {
      parser = SAXParserFactory.newInstance().newSAXParser().getXMLReader().getClass().getName();

      @SuppressWarnings("unused")
      XMLReader r = XMLReaderFactory.createXMLReader(parser);
    } catch (SAXException | ParserConfigurationException e) {
      e.printStackTrace();
    }
    return parser;
  }

  public static Map<String, String> getProperties(ClassLoader cl, String resource) {
    Properties properties = new Properties();
    InputStream in = getConfigAsStream(cl, resource);
    if (in != null) {
      try {
        properties.load(in);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    Map<String, String> map = new HashMap<>();
    for (Map.Entry<Object, Object> e : properties.entrySet()) {
      map.put(e.getKey().toString(), e.getValue().toString());
    }
    return map;
  }

  public static InputStream getConfigAsStream(ClassLoader cl, String resource) {

    File f = new File(resource);
    if (f.exists()) {
      try {

        return new FileInputStream(f);
      } catch (FileNotFoundException e) {

        e.printStackTrace();
      }
    } else {
      if (!resource.startsWith("/")) {
        resource = "/" + resource;
      }

      return cl.getResourceAsStream(resource);
    }
    return null;
  }
}
