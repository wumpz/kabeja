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

import org.kabeja.dxf.parser.DXFValue;
import org.kabeja.entities.Entity;
import org.kabeja.entities.Image;
import org.kabeja.entities.util.Utils;
import org.kabeja.math.Point3D;
import org.kabeja.util.Constants;

/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public class DXFImageHandler extends AbstractEntityHandler {
  public static final int GROUPCODE_IMAGEDEF_HARDREFERENCE = 340;
  public static final int GROUPCODE_VECTOR_U_X = 11;
  public static final int GROUPCODE_VECTOR_U_Y = 21;
  public static final int GROUPCODE_VECTOR_U_Z = 31;
  public static final int GROUPCODE_VECTOR_V_X = 12;
  public static final int GROUPCODE_VECTOR_V_Y = 22;
  public static final int GROUPCODE_VECTOR_V_Z = 32;
  public static final int GROUPCODE_IAMGESIZE_U = 13;
  public static final int GROUPCODE_IAMGESIZE_V = 23;
  public static final int GROUPCODE_DISPLAY_PROPERTY = 70;
  public static final int GROUPCODE_BRIGHTNESS = 281;
  public static final int GROUPCODE_CONTRAST = 282;
  public static final int GROUPCODE_FADE = 283;
  public static final int GROUPCODE_NUMBER_CLIP_BOUNDARY = 91;
  public static final int GROUPCODE_CLIP_BOUNDARY_X = 14;
  public static final int GROUPCODE_CLIP_BOUNDARY_Y = 24;
  public static final int GROUPCODE_CLIP_BOUNDARY_TYPE = 71;
  public static final int GROUPCODE_CLIPPING_STATE = 280;
  protected Image image;
  protected Point3D clippingPoint;

  @Override
  public String getDXFEntityType() {
    return Constants.ENTITY_TYPE_IMAGE;
  }

  @Override
  public void endDXFEntity() {}

  @Override
  public Entity getDXFEntity() {
    return image;
  }

  @Override
  public boolean isFollowSequence() {
    return false;
  }

  @Override
  public void parseGroup(int groupCode, DXFValue value) {
    switch (groupCode) {
      case GROUPCODE_START_X:
        image.getInsertPoint().setX(value.getDoubleValue());

        break;

      case GROUPCODE_START_Y:
        image.getInsertPoint().setY(value.getDoubleValue());

        break;

      case GROUPCODE_START_Z:
        image.getInsertPoint().setZ(value.getDoubleValue());

        break;

      case GROUPCODE_IMAGEDEF_HARDREFERENCE:
        image.setImageDefObjectID(Utils.parseIDString(value.getValue()));

        break;

      case GROUPCODE_VECTOR_U_X:
        image.getVectorU().setX(value.getDoubleValue());

        break;

      case GROUPCODE_VECTOR_U_Y:
        image.getVectorU().setY(value.getDoubleValue());

        break;

      case GROUPCODE_VECTOR_U_Z:
        image.getVectorU().setZ(value.getDoubleValue());

        break;

      case GROUPCODE_VECTOR_V_X:
        image.getVectorV().setX(value.getDoubleValue());

        break;

      case GROUPCODE_VECTOR_V_Y:
        image.getVectorV().setY(value.getDoubleValue());

        break;

      case GROUPCODE_VECTOR_V_Z:
        image.getVectorV().setZ(value.getDoubleValue());

        break;

      case GROUPCODE_IAMGESIZE_U:
        image.setImageSizeAlongU(value.getDoubleValue());

        break;

      case GROUPCODE_IAMGESIZE_V:
        image.setImageSizeAlongV(value.getDoubleValue());

        break;

      case GROUPCODE_CLIPPING_STATE:
        image.setClipping(value.getBooleanValue());

        break;

      case GROUPCODE_CLIP_BOUNDARY_X:
        clippingPoint = new Point3D();
        clippingPoint.setX(value.getDoubleValue());
        image.addClippingPoint(clippingPoint);

        break;

      case GROUPCODE_CLIP_BOUNDARY_Y:
        clippingPoint.setY(value.getDoubleValue());

        break;

      case GROUPCODE_BRIGHTNESS:
        image.setBrightness(value.getDoubleValue());

        break;

      case GROUPCODE_CONTRAST:
        image.setContrast(value.getDoubleValue());

        break;

      case GROUPCODE_FADE:
        image.setFade(value.getDoubleValue());

        break;

      case GROUPCODE_CLIP_BOUNDARY_TYPE:
        if (value.getIntegerValue() == 1) {
          image.setRectangularClipping(true);
        } else if (value.getIntegerValue() == 2) {
          image.setPolygonalClipping(true);
        }

        break;

      default:
        super.parseCommonProperty(groupCode, value, image);
    }
  }

  @Override
  public void startDXFEntity() {
    image = new Image();
    image.setDocument(doc);
  }
}
