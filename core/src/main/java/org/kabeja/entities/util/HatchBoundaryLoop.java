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

package org.kabeja.entities.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kabeja.common.DraftEntity;
import org.kabeja.entities.Entity;
import org.kabeja.math.Bounds;

public class HatchBoundaryLoop {
  private final List<DraftEntity> edges = new ArrayList<>();

  private int boundaryPathTypeFlag = BoundaryPathType.BOUNDARY_PATH_TYPE_FLAG_DEFAULT.id();

  public boolean getBoundaryPathTypeFlag(BoundaryPathType type) {
    return (boundaryPathTypeFlag & type.id()) != 0;
  }

  public void setBoundaryPathTypeFlag(BoundaryPathType type) {
    boundaryPathTypeFlag |= type.id();
  }

  public int getBoundaryPathTypeFlag() {
    return boundaryPathTypeFlag;
  }

  public void setBoundaryPathTypeFlag(int boundaryPathTypeFlag) {
    this.boundaryPathTypeFlag = boundaryPathTypeFlag;
  }

  public Iterator<DraftEntity> getBoundaryEdgesIterator() {
    return edges.iterator();
  }

  public List<DraftEntity> getBoundaryEdges() {
    return edges;
  }

  public void addBoundaryEdge(DraftEntity edge) {
    edges.add(edge);
  }

  public Bounds getBounds() {
    Bounds bounds = new Bounds();

    // System.out.println("edges="+edges.size());
    if (!edges.isEmpty()) {
      Iterator<DraftEntity> i = edges.iterator();

      while (i.hasNext()) {
        Entity entity = (Entity) i.next();
        Bounds b = entity.getBounds();

        if (b.isValid()) {
          bounds.addToBounds(b);
        }
      }

      return bounds;
    } else {
      bounds.setValid(false);

      return bounds;
    }
  }

  public int getEdgeCount() {
    return this.edges.size();
  }

  public enum BoundaryPathType {
    BOUNDARY_PATH_TYPE_FLAG_DEFAULT(0),
    BOUNDARY_PATH_TYPE_FLAG_EXTERNAL(1),
    BOUNDARY_PATH_TYPE_FLAG_POLYLINE(2),
    BOUNDARY_PATH_TYPE_FLAG_DERIVED(4),
    BOUNDARY_PATH_TYPE_FLAG_TEXTBOX(8),
    BOUNDARY_PATH_TYPE_FLAG_OUTERMOST(16);

    private final int id;

    private BoundaryPathType(int id) {
      this.id = id;
    }

    public int id() {
      return id;
    }
  }
}
