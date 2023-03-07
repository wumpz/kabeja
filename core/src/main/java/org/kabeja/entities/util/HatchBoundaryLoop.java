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

  private int boundaryPathTypeFlag = BoundaryPathType.DEFAULT.id();

  public boolean isBoundaryPathType(BoundaryPathType type) {
    return (boundaryPathTypeFlag & type.id()) != 0;
  }

  public void setBoundaryPathType(BoundaryPathType type) {
    boundaryPathTypeFlag |= type.id();
  }

  public int getBoundaryPathTypeFlag() {
    return boundaryPathTypeFlag;
  }

  public void setBoundaryPathTypeFlag(int boundaryPathTypeFlag) {
    this.boundaryPathTypeFlag = boundaryPathTypeFlag;
  }

  public String getBoundaryPathTypeDescription() {
    String log = "boundary path type: " + getBoundaryPathTypeFlag() + " -> ";

    if (getBoundaryPathTypeFlag() == 0) {
      log += "DEFAULT";
    } else {
      for (var flag : BoundaryPathType.values()) {
        if (flag.id != 0) {
          if (isBoundaryPathType(flag)) {
            log += flag.name() + " ";
          }
        }
      }
    }
    return log;
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
    DEFAULT(0),
    EXTERNAL(1),
    POLYLINE(2),
    DERIVED(4),
    TEXTBOX(8),
    OUTERMOST(16);

    private final int id;

    private BoundaryPathType(int id) {
      this.id = id;
    }

    public int id() {
      return id;
    }
  }
}
