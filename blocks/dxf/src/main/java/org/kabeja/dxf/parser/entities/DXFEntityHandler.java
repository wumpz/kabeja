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

import org.kabeja.dxf.parser.DXFHandler;
import org.kabeja.entities.Entity;

/**
 * This interface descripe an Entity handler, which should handle (parse) an DXF entity.
 *
 * <h3>Lifecycle</h3>
 *
 * <ol>
 *   <li>setDXFDocument
 *   <li>startDXFEntity
 *   <li>parseGroup (multiple)
 *   <li>isFollowSequence (need for polylines,hatches, where multiple vertices follow)
 *   <li>endDXFEntity
 *   <li>getDXFEntity </lo>
 *
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public interface DXFEntityHandler extends DXFHandler {
  /**
   * @return the DXFEntity name (LINE,POLYLINE,TEXT,...)
   */
  String getDXFEntityType();

  /** Will called if the entity block starts. */
  void startDXFEntity();

  /**
   * Called after endDXFEntity.
   *
   * @return the parsed Entity
   */
  Entity getDXFEntity();

  /** Will called if the entity block ends. */
  void endDXFEntity();

  /**
   * @return true if the this DXFEntityHandler have to parse the following entities (like POLYLINE),
   *     otherwise false (like TEXT,LINE).
   */
  boolean isFollowSequence();

  /**
   * @return true if this DXFEntityHandler is within an embedded object block.
   */
  boolean isEmbeddedObjectMode();

  /** resume normal group code handling and officially leave embedded object mode */
  void resetEmbeddedObjectMode();

  /**
   * Can block group processing entirely for a entity. This is used to block e.g. embeddedObjects.
   */
  public default boolean processGroups() {
    return !isEmbeddedObjectMode();
  }
}
