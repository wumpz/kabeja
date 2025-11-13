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

package org.kabeja.dxf.parser.entities;

import org.kabeja.DraftDocument;
import org.kabeja.common.Layer;
import org.kabeja.common.LineType;
import org.kabeja.dxf.parser.DXFValue;
import org.kabeja.entities.Entity;
import org.kabeja.entities.util.Utils;

/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth </a>
 */
public abstract class AbstractEntityHandler implements DXFEntityHandler {
  public static final int ELEMENT_REFERENCE = 5;
  public static final int GROUPCODE_START_X = 10;
  public static final int GROUPCODE_START_Y = 20;
  public static final int GROUPCODE_START_Z = 30;
  public static final int END_X = 11;
  public static final int END_Y = 21;
  public static final int END_Z = 31;
  public static final int LAYER_NAME = 8;
  public static final int TRANSPARENCY = 440;
  public static final int COLOR_CODE = 62;
  public static final int COLORNAME = 430;
  public static final int COLOR_24BIT = 420;
  public static final int COLOR_TRANSPARENCY = 440;
  public static final int FLAGS = 70;
  public static final int EXTRUSION_X = 210;
  public static final int EXTRUSION_Y = 220;
  public static final int EXTRUSION_Z = 230;
  public static final int VISIBILITY = 60;
  public static final int LINE_TYPE = 6;
  public static final int LINE_TYPE_SCALE = 48;
  public static final int LINE_WEIGHT = 370;
  public static final int GROUPCODE_THICKNESS = 39;
  public static final int GROUPCODE_STYLENAME = 3;
  public static final int GROUPCODE_TEXT = 1;
  public static final int GROUPCODE_ROTATION_ANGLE = 50;
  public static final int GROUPCODE_MODELSPACE = 67;

  public static final int GROUPCODE_EMBEDDED_OBJECT = 101;

  public static final int XDATA_STRING = 1000;
  public static final int XDATA_APPLICATION_NAME = 1001;
  public static final int XDATA_LONG = 1071;

  public static final int GROUPCODE_OWNER = 330;
  protected DraftDocument doc;

  private static Layer lastLayer;
  private boolean lastLayerPresent;

  @Override
  public void setDocument(DraftDocument doc) {
    this.doc = doc;
  }

  private boolean embeddedObjectMode = false;

  @Override
  public boolean isEmbeddedObjectMode() {
    return embeddedObjectMode;
  }

  @Override
  public void resetEmbeddedObjectMode() {
    this.embeddedObjectMode = false;
  }

  protected void parseCommonProperty(int groupCode, DXFValue value, Entity entity) {
    switch (groupCode) {
      case GROUPCODE_EMBEDDED_OBJECT:
        embeddedObjectMode = true;
        break;

      case ELEMENT_REFERENCE:
        entity.setID(Utils.parseIDString(value.getValue()));
        break;

      case LAYER_NAME:
        if (lastLayerPresent && lastLayer.getName().equals(value.getValue())) {
          entity.setLayer(lastLayer);
        } else {

          if (this.doc.containsLayer(value.getValue())) {
            Layer layer = this.doc.getLayer(value.getValue());
            entity.setLayer(layer);
            lastLayer = layer;
            lastLayerPresent = true;
          } else {
            Layer layer = new Layer();
            layer.setName(value.getValue());
            this.doc.addLayer(layer);
            entity.setLayer(layer);
            lastLayer = layer;
            lastLayerPresent = true;
          }
        }

        break;

      case FLAGS:
        entity.setFlags(value.getIntegerValue());

        break;

      case VISIBILITY:
        entity.setVisibile(!value.getBooleanValue());

        break;

      case LINE_TYPE:
        if (!value.getValue().equals("BYLAYER")) {
          if (this.doc.containsLineType(value.getValue())) {
            LineType ltype = this.doc.getLineType(value.getValue());
            entity.setLineType(ltype);
          } else {
            LineType ltype = new LineType();
            ltype.setName(value.getValue());
            this.doc.addLineType(ltype);
            entity.setLineType(ltype);
          }
        }
        break;

      case LINE_TYPE_SCALE:
        entity.setLinetypeScaleFactor(value.getDoubleValue());

        break;

      case COLOR_CODE:
        entity.setColor(value.getIntegerValue());

        break;

      case EXTRUSION_X:
        entity.getExtrusion().setX(value.getDoubleValue());

        break;

      case EXTRUSION_Y:
        entity.getExtrusion().setY(value.getDoubleValue());

        break;

      case EXTRUSION_Z:
        entity.getExtrusion().setZ(value.getDoubleValue());

        break;

      case COLOR_24BIT:
        String hexString = Integer.toHexString(value.getIntegerValue());
        if (hexString.length() == 6) {
          byte[] b = new byte[3];
          b[0] = (byte) Integer.parseInt(hexString.substring(0, 2), 16);
          b[1] = (byte) Integer.parseInt(hexString.substring(2, 4), 16);
          b[2] = (byte) Integer.parseInt(hexString.substring(4, 6), 16);
          entity.setColorRGB(b);
        }
        break;

      case COLOR_TRANSPARENCY:
        break;

      case LINE_WEIGHT:
        entity.setLineWeight(value.getIntegerValue());

        break;

      case GROUPCODE_THICKNESS:
        entity.setThickness(value.getDoubleValue());

        break;

      case GROUPCODE_MODELSPACE:
        entity.setModelSpace(value.getBooleanValue());
        break;

      case GROUPCODE_OWNER:
        entity.setOwnerID(Utils.parseIDString(value.getValue()));
        break;

      case XDATA_STRING:
        entity.setXDataString(value.getValue());
        break;

      case XDATA_APPLICATION_NAME:
        entity.addXData(value.getValue());
        break;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.entities.EntityHandler#getEntityName()
   */
  @Override
  public abstract String getDXFEntityType();

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.Handler#releaseDXFDocument()
   */
  @Override
  public void releaseDocument() {
    this.doc = null;
  }
}
