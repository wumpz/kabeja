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

package org.kabeja.entities;

import org.kabeja.common.Type;
import org.kabeja.math.Bounds;
import org.kabeja.math.Point3D;
import org.kabeja.math.TransformContext;

/**
 * @author <a href="mailto:simon.mieth@gmx.de>Simon Mieth</a>
 */
public class Shape extends Entity {
  protected Point3D insertPoint = new Point3D();
  protected double rotation = 0.0;
  protected double height = 0.0;
  protected double scaleFactor = 1.0;
  protected double obliqueAngle = 0.0;
  protected String name = "";

  @Override
  public Bounds getBounds() {
    Bounds bounds = new Bounds();
    bounds.setValid(false);

    return bounds;
  }

  @Override
  public Type<Shape> getType() {
    return Type.TYPE_SHAPE;
  }

  /**
   * @return Returns the height.
   */
  public double getHeight() {
    return height;
  }

  /**
   * @param height The height to set.
   */
  public void setHeight(double height) {
    this.height = height;
  }

  /**
   * @return Returns the insertPoint.
   */
  public Point3D getInsertPoint() {
    return insertPoint;
  }

  /**
   * @param insertPoint The insertPoint to set.
   */
  public void setInsertPoint(Point3D insertPoint) {
    this.insertPoint = insertPoint;
  }

  /**
   * @return Returns the name.
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return Returns the obliqueAngle.
   */
  public double getObliqueAngle() {
    return obliqueAngle;
  }

  /**
   * @param obliqueAngle The obliqueAngle to set.
   */
  public void setObliqueAngle(double obliqueAngle) {
    this.obliqueAngle = obliqueAngle;
  }

  /**
   * @return Returns the rotation.
   */
  public double getRotation() {
    return rotation;
  }

  /**
   * @param rotation The rotation to set.
   */
  public void setRotation(double rotation) {
    this.rotation = rotation;
  }

  /**
   * @return Returns the scaleFactor.
   */
  public double getScaleFactor() {
    return scaleFactor;
  }

  /**
   * @param scaleFactor The scaleFactor to set.
   */
  public void setScaleFactor(double scaleFactor) {
    this.scaleFactor = scaleFactor;
  }

  @Override
  public double getLength() {
    return 0;
  }

  /** Not implemented yet */
  @Override
  public void transform(TransformContext context) {

    this.setInsertPoint(context.transform(this.getInsertPoint()));
  }

  /**
   * @ToDo: implement this method
   *
   * <p>This is a planar entity, therefore this method should be implemented, otherwise
   * OCS-coordinates will be used regardless of calling this method.
   */
  @Override
  public void toWcs() {
    throw new UnsupportedOperationException();
  }
}
