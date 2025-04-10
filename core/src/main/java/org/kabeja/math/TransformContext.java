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

public class TransformContext {
  private double[][] transformMatrix;

  private double rotationAngle = 0.0;

  private double scaleX = 1.0;

  private double scaleY = 1.0;

  private double scaleZ = 1.0;

  private Vector translation = new Vector(1.0, 1.0, 1.0);

  public TransformContext() {
    transformMatrix =
        new double[][] {
          {1.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}
        };
  }

  public TransformContext(double[][] transformMatrix) throws IllegalArgumentException {
    if ((transformMatrix.length != 4) && (transformMatrix[0].length != 4)) {
      throw new IllegalArgumentException("Not a valid tranformation matrix.");
    }

    this.transformMatrix = transformMatrix;
  }

  public double getRotationZAxis() {
    return rotationAngle;
  }

  public void setRotationZAxis(double rotationAngle) {
    this.rotationAngle = rotationAngle;
  }

  public void setScale(double s) {
    this.setScale(s, s, s);
  }

  public void appendScale(double x, double y, double z) {
    this.scaleX = x;
    this.scaleY = y;
    this.scaleZ = z;

    double[][] m =
        new double[][] {
          {x, 0.0, 0.0, 0.0}, {0.0, y, 0.0, 0.0}, {0.0, 0.0, z, 0.0}, {0.0, 0.0, 0.0, 1.0}
        };
    this.transformMatrix = MathUtils.multiplyMatrixByMatrix(m, this.transformMatrix);
  }

  public void setScale(double x, double y, double z) {
    this.scaleX = x;
    this.scaleY = y;
    this.scaleZ = z;

    double[][] m =
        new double[][] {
          {x, 0.0, 0.0, 0.0}, {0.0, y, 0.0, 0.0}, {0.0, 0.0, z, 0.0}, {0.0, 0.0, 0.0, 1.0}
        };
    this.transformMatrix = MathUtils.multiplyMatrixByMatrix(this.transformMatrix, m);
  }

  public double getScaleX() {
    return scaleX;
  }

  public void setScaleX(double scaleX) {
    this.setScale(scaleX, 1.0, 1.0);
  }

  public double getScaleY() {
    return scaleY;
  }

  public void setScaleY(double scaleY) {
    this.setScale(1.0, scaleY, 1.0);
  }

  public double getScaleZ() {
    return scaleZ;
  }

  public void setScaleZ(double scaleZ) {
    this.setScale(1.0, 1.0, scaleZ);
  }

  public double[][] getTransformMatrix() {
    return transformMatrix;
  }

  public void setTransformMatrix(double[][] transformMatrix) {
    this.transformMatrix = transformMatrix;
  }

  public Vector getTranslation() {
    return translation;
  }

  public void setTranslation(Vector translation) {
    this.translation = translation;

    double[][] m =
        new double[][] {
          {1.0, 0.0, 0.0, translation.getX()},
          {0.0, 1.0, 0.0, translation.getY()},
          {0.0, 0.0, 1.0, translation.getZ()},
          {0.0, 0.0, 0.0, 1.0}
        };
    this.transformMatrix = MathUtils.multiplyMatrixByMatrix(this.transformMatrix, m);
  }

  public void appendTranslation(Vector translation) {

    double[][] m =
        new double[][] {
          {1.0, 0.0, 0.0, translation.getX()},
          {0.0, 1.0, 0.0, translation.getY()},
          {0.0, 0.0, 1.0, translation.getZ()},
          {0.0, 0.0, 0.0, 1.0}
        };
    this.transformMatrix = MathUtils.multiplyMatrixByMatrix(m, this.transformMatrix);
  }

  public Point3D transform(Point3D a) {
    double[] v = new double[] {a.getX(), a.getY(), a.getZ(), 1.0};
    v = MathUtils.multiplyMatrixByVector(this.transformMatrix, v);

    Point3D p = new Point3D(v[0], v[1], v[2]);

    return p;
  }

  public void debug() {
    System.out.println("TransformMatrix");
    System.out.println(
        this.transformMatrix[0][0]
            + "\t"
            + this.transformMatrix[0][1]
            + "\t"
            + this.transformMatrix[0][2]
            + "\t"
            + this.transformMatrix[0][3]);
    System.out.println(
        this.transformMatrix[1][0]
            + "\t"
            + this.transformMatrix[1][1]
            + "\t"
            + this.transformMatrix[1][2]
            + "\t"
            + this.transformMatrix[1][3]);
    System.out.println(
        this.transformMatrix[2][0]
            + "\t"
            + this.transformMatrix[2][1]
            + "\t"
            + this.transformMatrix[2][2]
            + "\t"
            + this.transformMatrix[2][3]);
    System.out.println(
        this.transformMatrix[3][0]
            + "\t"
            + this.transformMatrix[3][1]
            + "\t"
            + this.transformMatrix[3][2]
            + "\t"
            + this.transformMatrix[3][3]);
    System.out.println();
  }
}
