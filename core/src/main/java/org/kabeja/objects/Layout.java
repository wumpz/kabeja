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
   Copyright 2007 Simon Mieth

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.kabeja.objects;

import org.kabeja.math.Bounds;
import org.kabeja.math.Point3D;
import org.kabeja.math.Vector;

public class Layout extends PlotSettings {

  protected Bounds limits = new Bounds();
  protected Point3D insertPoint = new Point3D();
  protected Bounds extent = new Bounds();
  protected Point3D originUCS = new Point3D();
  protected Vector xAxisUCS = new Vector(1.0, 0.0, 0.0);
  protected Vector yAxisUCS = new Vector(0.0, 1.0, 0.0);
  protected double elevation;
  protected int tabOrder;
  protected int orthographicTypeOfUCS;
  protected String paperSpaceBlockID;
  protected String lastActiveViewportID;
  protected String namedUCSID;
  protected String baseUCSID;

  public Bounds getLimits() {
    return limits;
  }

  public void setLimits(Bounds limits) {
    this.limits = limits;
  }

  public Point3D getInsertPoint() {
    return insertPoint;
  }

  public void setInsertPoint(Point3D insertPoint) {
    this.insertPoint = insertPoint;
  }

  public Bounds getExtent() {
    return extent;
  }

  public void setExtent(Bounds extent) {
    this.extent = extent;
  }

  public Point3D getOriginUCS() {
    return originUCS;
  }

  public void setOriginUCS(Point3D originUCS) {
    this.originUCS = originUCS;
  }

  public Vector getXAxisUCS() {
    return xAxisUCS;
  }

  public void setXAxisUCS(Vector axisUCS) {
    xAxisUCS = axisUCS;
  }

  public Vector getYAxisUCS() {
    return yAxisUCS;
  }

  public void setYAxisUCS(Vector axisUCS) {
    yAxisUCS = axisUCS;
  }

  public double getElevation() {
    return elevation;
  }

  public void setElevation(double elevation) {
    this.elevation = elevation;
  }

  public int getTabOrder() {
    return tabOrder;
  }

  public void setTabOrder(int tabOrder) {
    this.tabOrder = tabOrder;
  }

  public int getOrthographicTypeOfUCS() {
    return orthographicTypeOfUCS;
  }

  public void setOrthographicTypeOfUCS(int orthographicTypeOfUCS) {
    this.orthographicTypeOfUCS = orthographicTypeOfUCS;
  }

  public String getPaperSpaceBlockID() {
    return paperSpaceBlockID;
  }

  public void setPaperSpaceBlockID(String paperSpaceBlockID) {
    this.paperSpaceBlockID = paperSpaceBlockID;
  }

  public String getLastActiveViewportID() {
    return lastActiveViewportID;
  }

  public void setLastActiveViewportID(String lastActiveViewportID) {
    this.lastActiveViewportID = lastActiveViewportID;
  }

  public String getNamedUCSID() {
    return namedUCSID;
  }

  public void setNamedUCSID(String namedUCSID) {
    this.namedUCSID = namedUCSID;
  }

  public String getBaseUCSID() {
    return baseUCSID;
  }

  public void setBaseUCSID(String baseUCSID) {
    this.baseUCSID = baseUCSID;
  }
}
