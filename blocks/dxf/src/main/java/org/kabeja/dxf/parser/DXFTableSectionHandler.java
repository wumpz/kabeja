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

package org.kabeja.dxf.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.kabeja.dxf.parser.table.DXFTableHandler;
import org.kabeja.parser.ParseException;

/**
 * @author <a href="mailto:simon.mieth@gmx.de>Simon Mieth</a>
 */
public class DXFTableSectionHandler extends AbstractSectionHandler implements DXFHandlerManager {
  public static final String sectionKey = "TABLES";
  public static final String TABLE_START = "TABLE";
  public static final String TABLE_END = "ENDTAB";
  public final int TABLE_CODE = 0;
  private String table = "";
  private DXFTableHandler handler;
  private final Map<String, DXFTableHandler> handlers = new HashMap<>();
  private boolean parse = false;

  public DXFTableSectionHandler() {}

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.SectionHandler#getSectionKey()
   */
  @Override
  public String getSectionKey() {
    return sectionKey;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.SectionHandler#parseGroup(int, java.lang.String)
   */
  @Override
  public void parseGroup(int groupCode, DXFValue value) throws ParseException {
    if (groupCode == TABLE_CODE) {
      // switch table
      if (TABLE_END.equals(value)) {
        table = "";

        if (parse) {
          handler.endParsing();
          parse = false;
        }
      } else if (TABLE_START.equals(value)) {
      } else {
        if (parse) {
          handler.endParsing();
        }

        table = value.getValue();

        if (handlers.containsKey(table)) {
          handler = (DXFTableHandler) handlers.get(table);
          handler.setDocument(this.doc);
          handler.startParsing();
          parse = true;
        } else {
          parse = false;
        }
      }
    } else {
      if (parse) {
        handler.parseGroup(groupCode, value);
      }
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.SectionHandler#endParsing()
   */
  @Override
  public void endSection() {}

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.SectionHandler#startParsing()
   */
  @Override
  public void startSection() {
    parse = false;
  }

  @Override
  public void addHandler(DXFHandler handler) {
    addDXFTableHandler((DXFTableHandler) handler);
  }

  public void addDXFTableHandler(DXFTableHandler handler) {
    handlers.put(handler.getTableType(), handler);
  }

  /* (non-Javadoc)
   * @see de.miethxml.kabeja.parser.Handler#releaseDXFDocument()
   */
  @Override
  public void releaseDocument() {
    this.doc = null;

    Iterator i = handlers.values().iterator();

    while (i.hasNext()) {
      DXFHandler handler = (DXFHandler) i.next();
      handler.releaseDocument();
    }
  }
}
