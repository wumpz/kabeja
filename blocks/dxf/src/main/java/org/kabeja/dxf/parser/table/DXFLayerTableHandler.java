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

package org.kabeja.dxf.parser.table;

import org.kabeja.common.Layer;
import org.kabeja.common.LineType;
import org.kabeja.dxf.parser.DXFValue;
import org.kabeja.util.Constants;

/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public class DXFLayerTableHandler extends AbstractTableHandler {
  public static final String TABLE_KEY = "LAYER";
  public static final int GROUPCODE_LAYER_NAME = 2;
  public static final int GROUPCODE_LAYER_LINETYPE = 6;
  public static final int GROUPCODE_LAYER_COLORNUMBER = 62;
  public static final int GROUPCODE_LAYER_PLOTTINGFLAG = 290;
  public static final int GROUPCODE_LAYER_LINEWEIGHT = 370;
  public static final int GROUPCODE_LAYER_PLOTSTYLENAME = 390;
  public static final int GROUPCODE_LAYER_COLOR_24BIT = 420;
  private Layer layer;

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.table.TableHandler#getTableKey()
   */
  @Override
  public String getTableType() {
    return TABLE_KEY;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.table.TableHandler#parseGroup(int,
   *      java.lang.String)
   */
  @Override
  public void parseGroup(int groupCode, DXFValue value) {
    switch (groupCode) {
      case GROUPCODE_LAYER_NAME:
        layer.setName(value.getValue());
        break;

      case GROUPCODE_LAYER_COLORNUMBER:
        layer.setColor(value.getIntegerValue());
        break;

      case GROUPCODE_LAYER_COLOR_24BIT:
        String hexString = Integer.toHexString(value.getIntegerValue());
        if (hexString.length() == 6) {
          byte[] b = new byte[3];
          b[0] = (byte) Integer.parseInt(hexString.substring(0, 2), 16);
          b[1] = (byte) Integer.parseInt(hexString.substring(2, 4), 16);
          b[2] = (byte) Integer.parseInt(hexString.substring(4, 6), 16);
          layer.setColorRGB(b);
        }
        break;

      case GROUPCODE_LAYER_LINETYPE:
        if (!value.getValue().equals("BYLAYER")) {
          LineType ltype = this.doc.getLineType(value.getValue());
          if (ltype == null) {
            ltype = new LineType();
            ltype.setName(value.getValue());
            this.doc.addLineType(ltype);
          }
          layer.setLineType(ltype);
        }
        break;

      case Constants.GROUPCODE_STANDARD_FLAGS:
        layer.setFlags(value.getIntegerValue());
        break;

      case GROUPCODE_LAYER_LINEWEIGHT:
        layer.setLineWeight(value.getIntegerValue());
        break;

      case GROUPCODE_LAYER_PLOTSTYLENAME:
        layer.setPlotStyle(value.getValue());
        break;

      case Constants.GROUPCODE_HANDLE:
        layer.setID(value.getValue());
        break;
      case GROUPCODE_LAYER_PLOTTINGFLAG:
        layer.setPlottable(!value.getBooleanValue());
        break;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.table.TableHandler#endParsing()
   */
  @Override
  public void endParsing() {
    this.doc.addLayer(layer);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.table.TableHandler#startParsing()
   */
  @Override
  public void startParsing() {
    layer = new Layer();
    layer.setDocument(this.doc);
  }
}
