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
/*
 * Created on Jun 29, 2004
 *
 */
package org.kabeja.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.kabeja.DraftDocument;
import org.kabeja.common.Type;
import org.kabeja.math.Bounds;
import org.kabeja.math.Extrusion;
import org.kabeja.math.MathUtils;
import org.kabeja.math.Point3D;
import org.kabeja.math.TransformContext;
import org.kabeja.math.Vector;

/**
 * @author <a href="mailto:simon.mieth@gmx.de>Simon Mieth </a>
 */
public class Polyline extends Entity {

  protected static final double QUARTER_CIRCLE_ANGLE = Math.tan(0.39269908169872414D);
  protected List<Vertex> vertices = new ArrayList<>();
  protected double startWidth = 0.0;
  protected double endWidth = 0.0;
  protected boolean constantWidth = true;
  protected int surefaceType = 0;
  protected int surefaceDensityRows = 0;
  protected int surefaceDensityColumns = 0;
  protected int rows = 0;
  protected int columns = 0;
  protected Point3D elevation = new Point3D(0, 0, 0);

  @Override
  public Bounds getBounds() {
    Bounds bounds = new Bounds();

    Iterator<Vertex> i = vertices.iterator();

    if (i.hasNext()) {
      Vertex last;
      Vertex first;
      Vertex v = null;

      last = first = (Vertex) i.next();
      bounds.addToBounds(last.getPoint());

      while (i.hasNext()) {
        v = (Vertex) i.next();
        addToBounds(last, v, bounds);
        last = v;
      }

      if ((v != null) && (v.getBulge() != 0.0)) {
        addToBounds(v, first, bounds);
      }
    } else {
      bounds.setValid(false);
    }

    return bounds;
  }

  public void addVertex(Vertex vertex) {
    vertices.add(vertex);
    vertex.setDocument(this.doc);
    if (!vertex.isConstantWidth()) {
      constantWidth = false;
    }
  }

  public int getVertexCount() {
    return this.vertices.size();
  }

  public List<Vertex> getVertices() {
    return this.vertices;
  }

  public void removeVertex(Vertex vertex) {
    // remove and check the constantwidth
    constantWidth = true;

    Iterator<Vertex> i = vertices.iterator();

    while (i.hasNext()) {
      Vertex v = (Vertex) i.next();

      if (v == vertex) {
        i.remove();
      } else if (!v.isConstantWidth()) {
        constantWidth = false;
      }
    }
  }

  public void removeVertex(int index) {
    constantWidth = true;

    for (int i = 0; i < vertices.size(); i++) {
      Vertex v = (Vertex) vertices.get(i);

      if (index == i) {
        vertices.remove(i);
      } else if (!v.isConstantWidth()) {
        constantWidth = false;
      }
    }
  }

  public Vertex getVertex(int i) {
    return (Vertex) vertices.get(i);
  }

  /**
   * Returns the distance between 2 Point3D
   *
   * @param start
   * @param end
   * @return the length between the two points
   */
  protected double getLength(Point3D start, Point3D end) {
    double value =
        Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));

    return value;
  }

  /**
   * Caculate the radius of a cut circle segment between 2 Vertex
   *
   * @param bulge the vertex bulge
   * @param length the length of the circle cut
   */
  public double getRadius(double bulge, double length) {
    double h = (bulge * length) / 2;
    double value = (h / 2) + (Math.pow(length, 2) / (8 * h));

    return Math.abs(value);
  }

  @Override
  public Type<Polyline> getType() {
    return Type.TYPE_POLYLINE;
  }

  /**
   * @return Returns the endWidth.
   */
  public double getEndWidth() {
    return endWidth;
  }

  /**
   * @param endWidth The endWidth to set.
   */
  public void setEndWidth(double endWidth) {
    this.endWidth = endWidth;
  }

  /**
   * @return Returns the startWidth.
   */
  public double getStartWidth() {
    return startWidth;
  }

  /**
   * @param startWidth The startWidth to set.
   */
  public void setStartWidth(double startWidth) {
    this.startWidth = startWidth;
  }

  public boolean isClosed() {
    // the closed Flag
    return (this.flags & 1) == 1;
  }

  public boolean isCurveFitVerticesAdded() {
    return (this.flags & 2) == 2;
  }

  public boolean isSplineFitVerticesAdded() {
    return (this.flags & 4) == 4;
  }

  public boolean is3DPolygon() {
    return (this.flags & 8) == 8;
  }

  public boolean is3DPolygonMesh() {
    return (this.flags & 16) == 16;
  }

  public boolean isPolyfaceMesh() {
    return (this.flags & 64) == 64;
  }

  public boolean isClosedMeshNDirection() {
    return (this.flags & 32) == 32;
  }

  public boolean isClosedMeshMDirection() {
    return (this.flags & 1) == 1;
  }

  public boolean isQuadSpline() {
    if (isSplineFitVerticesAdded()) {
      return this.surefaceType == 5;
    }

    return false;
  }

  public boolean isCubicSpline() {
    if (isSplineFitVerticesAdded()) {
      return this.surefaceType == 6;
    }

    return false;
  }

  public boolean isConstantWidth() {
    // TODO review to see if the
    // property is always set correct
    if (!this.constantWidth) {
      return false;
    } else {
      this.constantWidth = true;

      Iterator<Vertex> i = vertices.iterator();

      while (i.hasNext()) {
        Vertex vertex = (Vertex) i.next();

        if (!vertex.isConstantWidth()) {
          this.constantWidth = false;

          return this.constantWidth;
        }
      }
    }

    return this.constantWidth;
  }

  /**
   * @return Returns the surefaceType.
   */
  public int getSurefaceType() {
    return surefaceType;
  }

  /**
   * @param surefaceType The surefaceType to set.
   */
  public void setSurefaceType(int surefaceType) {
    this.surefaceType = surefaceType;
  }

  /**
   * @return Returns the columns.
   */
  public int getSurefaceDensityColumns() {
    return surefaceDensityColumns;
  }

  /**
   * @param columns The columns to set.
   */
  public void setSurefaceDensityColumns(int columns) {
    this.surefaceDensityColumns = columns;
  }

  /**
   * @return Returns the rows.
   */
  public int getSurefaceDensityRows() {
    return surefaceDensityRows;
  }

  /**
   * @param rows The rows to set.
   */
  public void setSurefaceDensityRows(int rows) {
    this.surefaceDensityRows = rows;
  }

  protected void addToBounds(Vertex start, Vertex end, Bounds bounds) {
    if (start.getBulge() != 0) {
      // calculte the height
      double l = MathUtils.distance(start.getPoint(), end.getPoint());

      // double h = Math.abs(last.getBulge()) * l / 2;
      double r = this.getRadius(start.getBulge(), l);

      double s = l / 2;
      Vector edgeDirection = MathUtils.getVector(start.getPoint(), end.getPoint());
      edgeDirection = MathUtils.normalize(edgeDirection);

      Point3D centerPoint = MathUtils.getPointOfStraightLine(start.getPoint(), edgeDirection, s);

      Vector centerPointDirection =
          MathUtils.crossProduct(edgeDirection, this.getExtrusion().getNormal());
      centerPointDirection = MathUtils.normalize(centerPointDirection);

      // double t = Math.sqrt(Math.pow(r, 2) - Math.pow(s, 2));
      // double t = 0;
      double h = Math.abs(start.getBulge() * l) / 2;

      // if(Math.abs(start.getBulge())>=1.0){
      // t = h-r;
      // }else{
      // //t = Math.sqrt(Math.pow(r, 2) - Math.pow(s, 2));
      // t=r-h;
      // }
      // the center point of the arc
      int startQ = 0;
      int endQ = 0;

      double bulge = start.getBulge();

      if (bulge > 0) {
        // the arc goes over the right side, but where is the center
        // point?
        if (bulge > 1.0) {
          double t = h - r;
          centerPoint = MathUtils.getPointOfStraightLine(centerPoint, centerPointDirection, t);
        } else {
          double t = r - h;
          centerPoint =
              MathUtils.getPointOfStraightLine(centerPoint, centerPointDirection, (-1 * t));
        }

        endQ = MathUtils.getQuadrant(end.getPoint(), centerPoint);
        startQ = MathUtils.getQuadrant(start.getPoint(), centerPoint);
      } else {
        // the arc goes over the left side, but where is the center
        // point?
        if (bulge < -1.0) {
          double t = h - r;
          centerPoint =
              MathUtils.getPointOfStraightLine(centerPoint, centerPointDirection, (-1 * t));
        } else {
          double t = r - h;
          centerPoint = MathUtils.getPointOfStraightLine(centerPoint, centerPointDirection, t);
        }

        startQ = MathUtils.getQuadrant(end.getPoint(), centerPoint);
        endQ = MathUtils.getQuadrant(start.getPoint(), centerPoint);
      }

      if (endQ < startQ) {
        endQ += 4;
      } else if ((endQ == startQ) && (Math.abs(start.getBulge()) > QUARTER_CIRCLE_ANGLE)) {
        endQ += 4;
      }

      while (endQ > startQ) {
        switch (startQ) {
          case 0:
            bounds.addToBounds(centerPoint.getX(), centerPoint.getY() + r, centerPoint.getZ());

            break;

          case 1:
            bounds.addToBounds(centerPoint.getX() - r, centerPoint.getY(), centerPoint.getZ());

            break;

          case 2:
            bounds.addToBounds(centerPoint.getX(), centerPoint.getY() - r, centerPoint.getZ());

            break;

          case 3:
            bounds.addToBounds(centerPoint.getX() + r, centerPoint.getY(), centerPoint.getZ());
            endQ -= 4;
            startQ -= 4;

            break;
        }

        startQ++;
      }
    }

    bounds.addToBounds(start.getPoint());
    bounds.addToBounds(end.getPoint());
  }

  public Vertex getPolyFaceMeshVertex(int index) {
    Iterator<Vertex> i = this.vertices.iterator();
    int count = 1;

    while (i.hasNext()) {
      Vertex v = (Vertex) i.next();

      if (v.isPolyFaceMeshVertex()) {
        if (count == index) {
          return v;
        } else {
          count++;
        }
      }
    }

    return null;
  }

  /**
   * @return Returns the column.
   */
  public int getColumns() {
    return columns;
  }

  /**
   * @param column The column to set.
   */
  public void setColumns(int column) {
    this.columns = column;
  }

  /**
   * @return Returns the rows.
   */
  public int getRows() {
    return rows;
  }

  /**
   * @param rows The rows to set.
   */
  public void setRows(int rows) {
    this.rows = rows;
  }

  public boolean isSimpleMesh() {
    return (this.surefaceType == 0) && ((this.flags & 4) == 0);
  }

  public boolean isQuadSurefaceMesh() {
    return (this.surefaceType == 5) && ((this.flags & 4) == 4);
  }

  public boolean isCubicSurefaceMesh() {
    return (this.surefaceType == 6) && ((this.flags & 4) == 4);
  }

  public boolean isBezierSurefaceMesh() {
    return (this.surefaceType == 8) && ((this.flags & 4) == 4);
  }

  @Override
  public double getLength() {
    double length = 0.0;

    if (isCubicSpline() || isQuadSpline()) {
      return getSplineApproximationLength();
    } else if (isPolyfaceMesh()) {
      return getPolyfaceLength();
    } else if (is3DPolygonMesh() || isBezierSurefaceMesh() || isCubicSurefaceMesh()) {
      return getMeshLength();
    } else {
      // a normal polyline with or without bulges
      Iterator<Vertex> i = this.vertices.iterator();
      Vertex first;
      Vertex last = first = (Vertex) i.next();

      while (i.hasNext()) {
        Vertex v = (Vertex) i.next();
        length += this.getSegmentLength(last, v);
        last = v;
      }

      if (this.isClosed()) {
        length += this.getSegmentLength(last, first);
      }
    }

    return length;
  }

  protected double getSegmentLength(Vertex start, Vertex end) {
    double l = MathUtils.distance(start.getPoint(), end.getPoint());

    if (start.getBulge() == 0.0) {
      return l;
    } else {
      double alpha = 4 * Math.atan(Math.abs(start.getBulge()));

      double r = l / (2 * Math.sin(alpha / 2));
      double d = (Math.PI * Math.toDegrees(alpha) * r) / 180;

      return d;
    }
  }

  protected double getSplineApproximationLength() {
    double length = 0.0;

    // use the approximation
    Iterator<Vertex> i = this.vertices.iterator();
    Vertex first;
    Vertex last = first = null;

    while (i.hasNext()) {
      Vertex v = (Vertex) i.next();

      if (v.is2DSplineApproximationVertex()) {
        if (first == null) {
          first = last = v;
        } else {
          length += getSegmentLength(last, v);
          last = v;
        }
      }
    }

    if (this.isClosed()) {
      length += getSegmentLength(last, first);
    }

    return length;
  }

  protected double getPolyfaceLength() {
    double length = 0.0;
    for (Vertex v : this.getVertices()) {
      if (v.isFaceRecord()) {
        Vertex v1 = getPolyFaceMeshVertex(v.getPolyFaceMeshVertex0());
        Vertex v2 = getPolyFaceMeshVertex(v.getPolyFaceMeshVertex1());
        Vertex v3 = getPolyFaceMeshVertex(v.getPolyFaceMeshVertex2());
        Vertex v4 = getPolyFaceMeshVertex(v.getPolyFaceMeshVertex3());

        if (v.isPolyFaceEdge0Visible() && (v.getPolyFaceMeshVertex0() != 0)) {
          length += getSegmentLength(v1, v2);
        }

        if (v.isPolyFaceEdge1Visible() && (v.getPolyFaceMeshVertex1() != 0)) {
          length += getSegmentLength(v2, v3);
        }

        if (v.isPolyFaceEdge2Visible() && (v.getPolyFaceMeshVertex2() != 0)) {
          length += getSegmentLength(v3, v4);
        }

        if (v.isPolyFaceEdge3Visible() && (v.getPolyFaceMeshVertex3() != 0)) {
          length += getSegmentLength(v4, v1);
        } else if ((v4 == null) && (v3 != null)) {
          // triangle
          length += getSegmentLength(v3, v1);
        }
      }
    }

    return length;
  }

  protected double getMeshLength() {
    double length = 0.0;

    if (isSimpleMesh()) {
      Vertex[][] points = new Vertex[this.rows][this.columns];
      Iterator<Vertex> it = this.vertices.iterator();

      // create a line for each row
      for (int i = 0; i < this.rows; i++) {
        for (int x = 0; x < this.columns; x++) {
          Vertex v = (Vertex) it.next();
          points[i][x] = v;

          if (x > 0) {
            length += getSegmentLength(points[i][x - 1], points[i][x]);
          }
        }

        if (isClosedMeshNDirection()) {
          length += getSegmentLength(points[i][points[i].length - 1], points[i][0]);
        }
      }

      // create a line for each column
      for (int i = 0; i < this.columns; i++) {
        for (int x = 0; x < this.rows; x++) {
          if (x > 0) {
            length += getSegmentLength(points[x - 1][i], points[x][i]);
          }
        }

        if (isClosedMeshMDirection()) {
          length += getSegmentLength(points[points[i].length - 1][i], points[0][i]);
        }
      }
    } else {
      Vertex[][] points = new Vertex[this.surefaceDensityRows][this.surefaceDensityColumns];
      Iterator<Vertex> vi = this.vertices.iterator();
      List<Vertex> appVertices = new ArrayList<>();

      while (vi.hasNext()) {
        Vertex v = (Vertex) vi.next();

        if (v.isMeshApproximationVertex()) {
          appVertices.add(v);
        }
      }

      Iterator<Vertex> it = appVertices.iterator();

      // create a line for each row
      for (int i = 0; i < this.surefaceDensityRows; i++) {
        for (int x = 0; x < this.surefaceDensityColumns; x++) {
          Vertex v = (Vertex) it.next();
          points[i][x] = v;

          if (x > 0) {
            length += getSegmentLength(points[i][x - 1], points[i][x]);
          }
        }

        if (isClosedMeshNDirection()) {
          length += getSegmentLength(points[i][points[i].length - 1], points[i][0]);
        }
      }

      // create a line for each column
      for (int i = 0; i < this.surefaceDensityColumns; i++) {
        for (int x = 0; x < this.surefaceDensityRows; x++) {
          if (x > 0) {
            length += getSegmentLength(points[x - 1][i], points[x][i]);
          }
        }

        if (isClosedMeshMDirection()) {
          length += getSegmentLength(points[points[i].length - 1][i], points[0][i]);
        }
      }
    }

    return length;
  }

  public void setClosed(boolean b) {
    if (b) {
      this.flags = this.flags | 1;
    } else if (this.isClosed()) {
      this.flags = this.flags ^ 1;
    }
  }

  /**
   * @return the elevation
   */
  public Point3D getElevation() {
    return elevation;
  }

  /**
   * @param elevation the elevation to set
   */
  public void setElevation(Point3D elevation) {
    this.elevation = elevation;
  }

  @Override
  public void setDocument(DraftDocument doc) {

    super.setDocument(doc);
    Iterator<Vertex> i = this.vertices.iterator();
    while (i.hasNext()) {
      ((Vertex) i.next()).setDocument(doc);
    }
  }

  /** Not implemented yet */
  @Override
  public void transform(TransformContext context) {}

  @Override
  public void toWcs() {
    Extrusion e = this.getExtrusion();
    boolean hasDefaultExtrusion = e.compareToNormalVector(0, 0, 1);
    if (hasDefaultExtrusion || is3D()) {
      return;
    }

    for (Vertex v : vertices) {
      v.transformToWcs(this.getExtrusion());
    }
    Extrusion newE = new Extrusion();
    newE.setX(0);
    newE.setY(0);
    newE.setZ(1);
    this.setExtrusion(newE);
  }

  private boolean is3D() {
    // 8: 3d polyline; 16: 3d polygon mesh
    return ((flags & 8) == 8) || ((flags & 16) == 16);
  }
}
