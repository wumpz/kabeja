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

import org.kabeja.common.Style;
import org.kabeja.dxf.parser.DXFValue;

/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public class DXFStyleTableHandler extends AbstractTableHandler {
  public static String TABLE_KEY = "STYLE";
  public static final int GROUPCODE_NAME = 2;
  public static final int GROUPCODE_TEXT_HEIGHT = 40;
  public static final int GROUPCODE_WIDTH_FACTOR = 41;
  public static final int GROUPCODE_OBLIQUE_ANGLE = 50;
  public static final int GROUPCODE_TEXT_GENERATION_FLAG = 71;
  public static final int GROUPCODE_FLAGS = 70;
  public static final int GROUPCODE_LAST_HEIGHT = 42;
  public static final int GROUPCODE_FONT_FILE = 3;
  public static final int GROUPCODE_BIG_FONT_FILE = 4;
  private Style style;

  /* (non-Javadoc)
   * @see de.miethxml.kabeja.parser.table.DXFTableHandler#endParsing()
   */
  @Override
  public void endParsing() {
    doc.addStyle(style);
  }

  /* (non-Javadoc)
   * @see de.miethxml.kabeja.parser.table.DXFTableHandler#getTableKey()
   */
  @Override
  public String getTableType() {
    // TODO Auto-generated method stub
    return TABLE_KEY;
  }

  /* (non-Javadoc)
   * @see de.miethxml.kabeja.parser.table.DXFTableHandler#parseGroup(int, de.miethxml.kabeja.parser.DXFValue)
   */
  @Override
  public void parseGroup(int groupCode, DXFValue value) {
    switch (groupCode) {
      case GROUPCODE_NAME:
        style.setName(value.getValue());

        break;

      case GROUPCODE_TEXT_HEIGHT:
        style.setTextHeight(value.getDoubleValue());

        break;

      case GROUPCODE_WIDTH_FACTOR:
        style.setWidthFactor(value.getDoubleValue());

        break;

      case GROUPCODE_OBLIQUE_ANGLE:
        style.setObliqueAngle(value.getDoubleValue());

        break;

      case GROUPCODE_TEXT_GENERATION_FLAG:
        style.setTextGenerationFlag(value.getIntegerValue());

        break;

      case GROUPCODE_LAST_HEIGHT:
        style.setLastHeight(value.getDoubleValue());

        break;

      case GROUPCODE_FONT_FILE:
        style.setFontFile(value.getValue());

        break;

      case GROUPCODE_BIG_FONT_FILE:
        style.setBigFontFile(value.getValue());

        break;

      case GROUPCODE_FLAGS:
        style.setFlags(value.getIntegerValue());

        break;
    }
  }

  /* (non-Javadoc)
   * @see de.miethxml.kabeja.parser.table.DXFTableHandler#startParsing()
   */
  @Override
  public void startParsing() {
    style = new Style();
  }
}
