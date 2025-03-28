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

import org.kabeja.DraftDocument;
import org.kabeja.common.Block;
import org.kabeja.entities.Entity;
import org.kabeja.entities.util.Utils;
import org.kabeja.parser.ParseException;
import org.kabeja.util.Constants;

/**
 * @author <a href="mailto:simon.mieth@gmx.de>Simon Mieth</a>
 */
public class DXFBlocksSectionHandler extends DXFEntitiesSectionHandler {
  public static final String SECTION_KEY = "BLOCKS";
  public static final String BLOCK_START = "BLOCK";
  public static final String BLOCK_END = "ENDBLK";
  public static final int BLOCK = 0;
  public static final int BLOCK_NAME = 2;
  public static final int BLOCK_NAME2 = 3;
  public static final int BLOCK_DESCRIPTION = 4;
  public static final int BLOCK_XREFPATHNAME = 1;
  public static final int BLOCK_BASE_X = 10;
  public static final int BLOCK_BASE_Y = 20;
  public static final int BLOCK_BASE_Z = 30;
  protected boolean parseBlockHeader = false;
  private Block block;

  /** */
  public DXFBlocksSectionHandler() {
    super();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.DXFSectionHandler#getSectionKey()
   */
  @Override
  public String getSectionKey() {
    return SECTION_KEY;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.DXFSectionHandler#parseGroup(int,
   *      org.dxf2svg.parser.DXFValue)
   */
  @Override
  public void parseGroup(int groupCode, DXFValue value) throws ParseException {
    switch (groupCode) {
      case BLOCK:
        if (BLOCK_START.equals(value.getValue())) {
          // handle
          parseBlockHeader = true;

          block = new Block();
        } else if (BLOCK_END.equals(value.getValue())) {
          // handle
          endEntity();
          parseBlockHeader = true;
          doc.addBlock(block);

        } else {
          // an entity
          parseBlockHeader = false;
          super.parseGroup(groupCode, value);
        }

        break;

      case BLOCK_NAME:
        if (parseBlockHeader) {
          block.setName(value.getValue());
        } else {
          super.parseGroup(groupCode, value);
        }

        break;

      case BLOCK_NAME2:
        if (parseBlockHeader) {
        } else {
          super.parseGroup(groupCode, value);
        }

        break;

      case BLOCK_DESCRIPTION:
        if (parseBlockHeader) {
          block.setDescription(value.getValue());
        } else {
          super.parseGroup(groupCode, value);
        }

        break;

      case Constants.GROUPCODE_STANDARD_LAYER:
        if (parseBlockHeader) {
          if (this.doc.containsLayer(value.getValue())) {
            block.setLayer(this.doc.getLayer(value.getValue()));
          } else {
            block.setLayer(doc.getRootLayer());
          }
        } else {
          super.parseGroup(groupCode, value);
        }

        break;

      case BLOCK_BASE_X:
        if (parseBlockHeader) {
          block.getReferencePoint().setX(value.getDoubleValue());
        } else {
          super.parseGroup(groupCode, value);
        }

        break;

      case BLOCK_BASE_Y:
        if (parseBlockHeader) {
          block.getReferencePoint().setY(value.getDoubleValue());
        } else {
          super.parseGroup(groupCode, value);
        }

        break;

      case BLOCK_BASE_Z:
        if (parseBlockHeader) {
          block.getReferencePoint().setZ(value.getDoubleValue());
        } else {
          super.parseGroup(groupCode, value);
        }

        break;

      case Constants.GROUPCODE_HANDLE:
        if (parseBlockHeader) {
          block.setID(Utils.parseIDString(value.getValue()));
        } else {
          super.parseGroup(groupCode, value);
        }
        break;

      case Constants.GROUPCODE_STANDARD_FLAGS:
        if (parseBlockHeader) {
          block.setFlags(value.getIntegerValue());
        } else {
          super.parseGroup(groupCode, value);
        }
        break;

      default:
        super.parseGroup(groupCode, value);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.DXFSectionHandler#setDXFDocument(org.dxf2svg.dxf.DXFDocument)
   */
  @Override
  public void setDocument(DraftDocument doc) {
    super.setDocument(doc);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.DXFSectionHandler#startSection()
   */
  @Override
  public void startSection() {
    parseEntity = false;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.DXFSectionHandler#endSection()
   */
  @Override
  public void endSection() {
    // endEntity();
  }

  @Override
  protected void endEntity() {
    if (parseEntity) {
      handler.endDXFEntity();

      Entity entity = handler.getDXFEntity();
      block.addEntity(entity);
      parseEntity = false;
    }
  }
}
