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

import java.util.Map;
import java.util.TreeMap;
import org.kabeja.DraftDocument;
import org.kabeja.dxf.parser.entities.DXFEntityHandler;
import org.kabeja.entities.Entity;
import org.kabeja.parser.ParseException;

/**
 * @author <a href="mailto:simon.mieth@gmx.de>Simon Mieth</a>
 */
public class DXFEntitiesSectionHandler extends AbstractSectionHandler
    implements DXFSectionHandler, DXFHandlerManager {
  private static String SECTION_KEY = "ENTITIES";
  public static final int ENTITY_START = 0;
  protected Map<String, DXFHandler> handlers = new TreeMap<>();
  protected DXFEntityHandler handler = null;
  protected boolean parseEntity = false;

  public DXFEntitiesSectionHandler() {}

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.SectionHandler#getSectionKey()
   */
  @Override
  public String getSectionKey() {
    return SECTION_KEY;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.SectionHandler#parseGroup(int, java.lang.String)
   */
  @Override
  public void parseGroup(int groupCode, DXFValue value) throws ParseException {
    if (groupCode == ENTITY_START) {
      if (parseEntity) {
        if (handler.isFollowSequence()) {
          // there is a sequence like polyline
          handler.parseGroup(groupCode, value);
          return;
        } else {
          endEntity();
          // check for reuse
          if (handler.getDXFEntityType().equals(value.getValue())) {
            handler.resetEmbeddedObjectMode();
            handler.startDXFEntity();
            parseEntity = true;
            return;
          }
        }
      }

      if (handlers.containsKey(value.getValue())) {
        // get handler for the new entity
        handler = (DXFEntityHandler) handlers.get(value.getValue());
        handler.setDocument(this.doc);
        handler.resetEmbeddedObjectMode();
        handler.startDXFEntity();
        parseEntity = true;
      } else {
        // no handler found
        parseEntity = false;
      }
    } else if (parseEntity) {
      // handler.setDXFDocument(this.doc);
      handler.parseGroup(groupCode, value);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.SectionHandler#setDXFDocument(org.dxf2svg.xml.DXFDocument)
   */
  @Override
  public void setDocument(DraftDocument doc) {
    this.doc = doc;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.SectionHandler#endParsing()
   */
  @Override
  public void endSection() {
    endEntity();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.SectionHandler#startParsing()
   */
  @Override
  public void startSection() {
    parseEntity = false;
  }

  protected void endEntity() {
    if (parseEntity) {
      handler.endDXFEntity();
      Entity entity = handler.getDXFEntity();
      doc.addEntity(entity);
    }
  }

  public void addDXFEntityHandler(DXFEntityHandler handler) {
    handler.setDocument(doc);
    handlers.put(handler.getDXFEntityType(), handler);
  }

  @Override
  public void addHandler(DXFHandler handler) {
    addDXFEntityHandler((DXFEntityHandler) handler);
  }

  /* (non-Javadoc)
   * @see de.miethxml.kabeja.parser.Handler#releaseDXFDocument()
   */
  @Override
  public void releaseDocument() {
    this.doc = null;

    for (DXFHandler handler : handlers.values()) {
      handler.releaseDocument();
    }
  }
}
