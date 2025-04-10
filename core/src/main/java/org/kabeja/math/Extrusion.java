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

package org.kabeja.math;

import org.kabeja.util.Constants;

/**
 * This class implements the arbitrary axis algorithm to extract the direction x,y,z of the plane
 * defined by the extrusion.
 *
 * @author <a href="mailto:simon.mieth@gmx.de>Simon Mieth</a>
 */
public class Extrusion {
  private static final double v = 1.0 / 64.0;
  protected Vector n = new Vector(0.0, 0.0, 1.0);

  /**
   * @return the x value of the extrusion direction.
   */
  public double getX() {
    return n.getX();
  }

  /** Set the x value of the extrusion direction. */
  public void setX(double x) {
    n.setX(x);
  }

  /**
   * @return the y value of the extrusion direction.
   */
  public double getY() {
    return n.getY();
  }

  /** Set the x value of the extrusion direction. */
  public void setY(double y) {
    n.setY(y);
  }

  /**
   * @return the z value of the extrusion direction.
   */
  public double getZ() {
    return n.getZ();
  }

  /** Set the x value of the extrusion direction. */
  public void setZ(double z) {
    n.setZ(z);
  }

  /**
   * Calculate and returns the x direction of the plane.
   *
   * @return
   */
  public Vector getDirectionX() {
    Vector directionX;
    if (normalIsCloseToWorldZ()) {
      directionX = MathUtils.crossProduct(Constants.DEFAULT_Y_AXIS_VECTOR, n);
    } else {
      directionX = MathUtils.crossProduct(Constants.DEFAULT_Z_AXIS_VECTOR, n);
    }
    directionX.normalize();
    return directionX;
  }

  /**
   * Calculate the y direction of the plane.
   *
   * @return the calculate y direction
   */
  public Vector getDirectionY() {
    Vector directionY = MathUtils.crossProduct(n, getDirectionX());
    directionY.normalize();
    return directionY;
  }

  public Point3D extrudePoint(Point3D basePoint, double elevation) {
    return MathUtils.getPointOfStraightLine(basePoint, this.n, elevation);
  }

  /**
   * Return the normal direction of the plane.
   *
   * @return
   */
  public Vector getNormal() {
    return n;
  }

  /**
   * @see getNormal()
   * @return
   */
  public Vector getDirectionZ() {
    return n;
  }

  /**
   * This method is used to determine if the normal is close to the WCS Z-Axis, according to the
   * arbitrary axis algorithm.
   */
  public boolean normalIsCloseToWorldZ() {
    return (Math.abs(n.getX()) < v) && (Math.abs(n.getY()) < v);
  }

  /**
   * Transforms a point from WCS to OCS. Relies on the arbitrary axis algorithm to determine the
   * OCS.
   *
   * <p>Edit: <a href="https://ezdxf.readthedocs.io/en/stable/concepts/ocs.html">This</a> may be the
   * source for both this method and also for {@link #transformOcsToWcs(Point3D)}. Our code is very
   * similar to the code examples I found there.
   *
   * @param p point in WCS
   * @return point in OCS
   */
  public Point3D wcsToOcs(Point3D p) {
    Vector ox = getDirectionX();
    Vector oy = getDirectionY();
    Vector oz = n;

    double x = p.x * ox.x + p.y * ox.y + p.z * ox.z;
    double y = p.x * oy.x + p.y * oy.y + p.z * oy.z;
    double z = p.x * oz.x + p.y * oz.y + p.z * oz.z;

    return new Point3D(x, y, z);
  }

  /**
   * Transforms a point from OCS to WCS. Does not simply translate the coordinates, but "reverts"
   * the arbitrary axis algorithm's effects.
   *
   * @param p
   * @return
   */
  public Point3D transformOcsToWcs(Point3D p) {
    Point3D wx = wcsToOcs(new Point3D(1, 0, 0));
    Point3D wy = wcsToOcs(new Point3D(0, 1, 0));
    Point3D wz = wcsToOcs(new Point3D(0, 0, 1));

    double x = p.x * wx.x + p.y * wx.y + p.z * wx.z;
    double y = p.x * wy.x + p.y * wy.y + p.z * wy.z;
    double z = p.x * wz.x + p.y * wz.y + p.z * wz.z;

    return new Point3D(x, y, z);
  }

  public boolean compareToNormalVector(Vector vector) {
    if (vector == null) {
      return false;
    } else {
      return this.getNormal().equals(vector);
    }
  }

  public boolean compareToNormalVector(double x, double y, double z) {
    return this.getNormal().equals(new Vector(x, y, z));
  }
}
