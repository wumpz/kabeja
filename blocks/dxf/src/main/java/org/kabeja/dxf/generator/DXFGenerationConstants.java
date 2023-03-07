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
package org.kabeja.dxf.generator;

public class DXFGenerationConstants {

  public static final String DXFGENERATOR_MANAGER = "dxfgenerator.manager";

  public static final int DXF_ENITY_TYPE_SUBCLASS_MARKER = 100;
  public static final int DXF_ENITY_TYPE_SUBCLASS_MARKER_1 = 10000;
  public static final int DXF_ENITY_TYPE_SUBCLASS_MARKER_2 = 10001;
  public static final int DXF_ENITY_TYPE_SUBCLASS_MARKER_3 = 10002;
  public static final int DXF_ENITY_TYPE_SUBCLASS_MARKER_4 = 10000;

  public static final int DXF_CHILDREN_INSERT_MARK = 30000;

  public static final String DEFAULT_TEXT_HEIGHT = "dxf.default.textheight";
  public static final String DEFAULT_ENCODING = "ISO-8859-1";
  public static final String DXF_ENCODING = "dxf.encoding";

  public static final String ATTRIBUTE_DEFAULT_PROFILE = "profile.default";

  public static final String SUBTYPE_ACDB_PROXYENTITY = "AcDbProxyEntity";
  public static final String SUBTYPE_ACDB_ENTITY = "AcDbEntity";
  public static final String SUBTYPE_ACDB_FACE = "AcDbFace";
  public static final String SUBTYPE_ACDB_MODELERGEOMETRY = "AcDbModelerGeometry";

  public static final String SUBTYPE_ACDB_CIRCLE = "AcDbCircle";
  public static final String SUBTYPE_ACDB_ARC = "AcDbArc";
  public static final String SUBTYPE_ACDB_TEXT = "AcDbText";
  public static final String SUBTYPE_ACDB_ATTRIBUTEDEFINITION = "AcDbAttributeDefinition";
  public static final String SUBTYPE_ACDB_ATTRIBUTE = "AcDbAttribute";
  public static final String SUBTYPE_ACDB_DIMENSION = "AcDbDimension";
  public static final String SUBTYPE_ACDB_ALIGNEDDIMENSION = "AcDbAlignedDimension";
  public static final String SUBTYPE_ACDB_ROTATEDDIMENSION = "AcDbRotatedDimension";
  public static final String SUBTYPE_ACDB_RADIALDIMENSION = "AcDbRadialDimension";
  public static final String SUBTYPE_ACDB_DIAMETRICDIMENSION = "AcDbDiametricDimension";
  public static final String SUBTYPE_ACDB_3POINTANGULARDIMENSION = "AcDb3PointAngularDimension";
  public static final String SUBTYPE_ACDB_ORDINATEDIMENSION = "AcDbOrdinateDimension";
  public static final String SUBTYPE_ACDB_ELLIPSE = "AcDbEllipse";
  public static final String SUBTYPE_ACDB_HATCH = "AcDbHatch";
  public static final String SUBTYPE_ACDB_RASTERIMAGE = "AcDbRasterImage";
  public static final String SUBTYPE_ACDB_BLOCKREFERENCE = "AcDbBlockReference";
  public static final String SUBTYPE_ACDB_LEADER = "AcDbLeader";
  public static final String SUBTYPE_ACDB_POLYLINE = "AcDbPolyline";
  public static final String SUBTYPE_ACDB_MLINE = "AcDbMline";
  public static final String SUBTYPE_ACDB_MTEXT = "AcDbMText";
  public static final String SUBTYPE_ACDB_OLEFRAME = "AcDbOleFrame";
  public static final String SUBTYPE_ACDB_OLE2FRAME = "AcDbOle2Frame";
  public static final String SUBTYPE_ACDB_POINT = "AcDbPoint";
  public static final String SUBTYPE_ACDB_2DPOLYLINE = "AcDb2dPolyline";
  public static final String SUBTYPE_ACDB_3DPOLYLINE = "AcDb3dPolyline";
  public static final String SUBTYPE_ACDB_RAY = "AcDbRay";
  public static final String SUBTYPE_ACDB_SHAPE = "AcDbShape";
  public static final String SUBTYPE_ACDB_TRACE = "AcDbTrace";
  public static final String SUBTYPE_ACDB_SPLINE = "AcDbSpline";
  public static final String SUBTYPE_ACDB_TABLE = "AcDbTable";
  public static final String SUBTYPE_ACDB_FCF = "AcDbFcf";
  public static final String SUBTYPE_ACDB_VERTEX = "AcDbVertex";
  public static final String SUBTYPE_ACDB_2DVERTEX = "AcDb2dVertex";
  public static final String SUBTYPE_ACDB_3DPOLYLINEVERTEX = "AcDb3dPolylineVertex";
  public static final String SUBTYPE_ACDB_VIEWPORT = "AcDbViewport";
  public static final String SUBTYPE_ACDB_XLINE = "AcDbXline";
}
