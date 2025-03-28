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
import org.kabeja.entities.Ray;
import org.kabeja.util.Constants;

/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public class DXFRayHandler extends AbstractEntityHandler {
  protected Ray ray;

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.entities.DXFEntityHandler#getDXFEntityName()
   */
  @Override
  public String getDXFEntityType() {
    return Constants.ENTITY_TYPE_RAY;
  }

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.entities.DXFEntityHandler#endDXFEntity()
   */
  @Override
  public void endDXFEntity() {}

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.entities.DXFEntityHandler#getDXFEntity()
   */
  @Override
  public Entity getDXFEntity() {
    return this.ray;
  }

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.entities.DXFEntityHandler#isFollowSequence()
   */
  @Override
  public boolean isFollowSequence() {
    return false;
  }

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.entities.DXFEntityHandler#parseGroup(int,
   *      de.miethxml.kabeja.parser.DXFValue)
   */
  @Override
  public void parseGroup(int groupCode, DXFValue value) {
    switch (groupCode) {
      case GROUPCODE_START_X:
        this.ray.getBasePoint().setX(value.getDoubleValue());

        break;

      case GROUPCODE_START_Y:
        this.ray.getBasePoint().setY(value.getDoubleValue());

        break;

      case GROUPCODE_START_Z:
        this.ray.getBasePoint().setZ(value.getDoubleValue());

        break;

      case END_X:
        this.ray.getDirection().setX(value.getDoubleValue());

        break;

      case END_Y:
        this.ray.getDirection().setY(value.getDoubleValue());

        break;

      case END_Z:
        this.ray.getDirection().setZ(value.getDoubleValue());

        break;

      default:
        super.parseCommonProperty(groupCode, value, ray);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.entities.DXFEntityHandler#startDXFEntity()
   */
  @Override
  public void startDXFEntity() {
    this.ray = new Ray();
    ray.setDocument(doc);
  }
}
