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
Copyright 2005 Simon Mieth

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
package org.kabeja.dxf.parser.entities;

import java.util.Hashtable;
import java.util.Iterator;
import org.kabeja.DraftDocument;
import org.kabeja.dxf.parser.DXFValue;
import org.kabeja.entities.Entity;
import org.kabeja.entities.Hatch;
import org.kabeja.entities.Polyline;
import org.kabeja.entities.Vertex;
import org.kabeja.entities.util.HatchBoundaryLoop;
import org.kabeja.entities.util.HatchLineFamily;
import org.kabeja.entities.util.HatchPattern;
import org.kabeja.parser.ParseException;
import org.kabeja.util.Constants;

/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public class DXFHatchHandler extends AbstractEntityHandler {
  public static final String END_SEQUENCE = "SEQEND";
  public static final int END_SEQUENCE_CODE = -2;
  public static final int GROUPCODE_ASSOSIATIVITY_FLAG = 71;
  public static final int GROUPCODE_BOUNDARY_ANNOTATION = 73;
  public static final int GROUPCODE_BOUNDARY_EDGE_COUNT = 93;
  public static final int GROUPCODE_BOUNDARY_EDGE_TYPE = 72;
  public static final int GROUPCODE_BOUNDARY_LOOP_COUNT = 91;
  public static final int GROUPCODE_BOUNDAYY_LOOP_TYPE = 92;
  public static final int GROUPCODE_DEFINITION_LINE_COUNT = 78;
  public static final int GROUPCODE_DEGENERTE_BOUNDARY_PATH_COUNT = 99;
  public static final int GROUPCODE_HATCH_DOUBLE_FLAG = 77;
  public static final int GROUPCODE_HATCH_STYLE = 75;
  public static final int GROUPCODE_NAME = 2;
  public static final int GROUPCODE_OFFSET_VECTOR = 11;
  public static final int GROUPCODE_PATTERN_ANGLE = 52;
  public static final int GROUPCODE_PATTERN_BASE_X = 43;
  public static final int GROUPCODE_PATTERN_BASE_Y = 44;
  public static final int GROUPCODE_PATTERN_FILL_COLOR = 63;
  public static final int GROUPCODE_PATTERN_LINE_ANGLE = 53;
  public static final int GROUPCODE_PATTERN_LINE_COUNT = 79;
  public static final int GROUPCODE_PATTERN_LINE_TYPE_DATA = 49;
  public static final int GROUPCODE_PATTERN_OFFSET_X = 45;
  public static final int GROUPCODE_PATTERN_OFFSET_Y = 46;
  public static final int GROUPCODE_PATTERN_SCALE = 41;
  public static final int GROUPCODE_PATTERN_TYPE = 76;
  public static final int GROUPCODE_PIXEL_SIZE = 47;
  public static final int GROUPCODE_SEED_POINTS_COUNT = 98;
  public static final int GROUPCODE_SOLID_FILL_FLAG = 70;
  protected DXFEntityHandler boundaryHandler;
  protected Hashtable boundaryHandlers = new Hashtable();
  protected int count;
  private boolean follow = false;
  private Hatch hatch;
  protected HatchLineFamily linePattern = new HatchLineFamily();
  protected HatchBoundaryLoop loop;
  protected double[] parameters = new double[0];
  private boolean parseBoundary = false;
  protected HatchPattern pattern;
  protected Polyline polyline;
  private boolean polylineBoundary = false;
  protected Vertex vertex;
  protected int lastGroupCode;

  public DXFHatchHandler() {
    init();
  }

  protected void endBoundaryElement() {
    if (boundaryHandler != null) {
      // get the last parsed entity
      this.boundaryHandler.endDXFEntity();
      this.loop.addBoundaryEdge(boundaryHandler.getDXFEntity());
      this.boundaryHandler = null;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.entities.DXFEntityHandler#endDXFEntity()
   */
  @Override
  public void endDXFEntity() {
    // this.linePattern.setPattern(this.parameters);
    if (this.pattern != null) {
      this.hatch.setHatchPatternID(this.pattern.getID());
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.entities.DXFEntityHandler#getDXFEntity()
   */
  @Override
  public Entity getDXFEntity() {
    return hatch;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.dxf2svg.parser.entities.DXFEntityHandler#getDXFEntityName()
   */
  @Override
  public String getDXFEntityType() {
    return Constants.ENTITY_TYPE_HATCH;
  }

  protected void init() {
    DXFEntityHandler handler = new DXFSplineHandler();
    boundaryHandlers.put(handler.getDXFEntityType(), handler);

    handler = new DXFLineHandler();
    boundaryHandlers.put(handler.getDXFEntityType(), handler);

    handler = new DXFArcHandler();
    boundaryHandlers.put(handler.getDXFEntityType(), handler);

    handler = new DXFEllipseHandler();
    boundaryHandlers.put(handler.getDXFEntityType(), handler);
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

  protected void parseBoundary(int groupCode, DXFValue value) throws ParseException {
    if (this.polylineBoundary) {
      parsePolylineBoundary(groupCode, value);
    } else {
      // delegate to the entityhandler
      this.boundaryHandler.parseGroup(groupCode, value);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see de.miethxml.kabeja.parser.entities.DXFEntityHandler#parseGroup(int,
   *      de.miethxml.kabeja.parser.DXFValue)
   */
  @Override
  public void parseGroup(int groupCode, DXFValue value) throws ParseException {
    switch (groupCode) {
      case GROUPCODE_BOUNDARY_LOOP_COUNT:
        break;

      case GROUPCODE_BOUNDARY_EDGE_COUNT:
        break;

      case GROUPCODE_BOUNDARY_EDGE_TYPE:
        if (this.lastGroupCode == GROUPCODE_BOUNDAYY_LOOP_TYPE) {
          this.polylineBoundary = true;
          this.polyline = new Polyline();
          this.polyline.setDocument(this.doc);
          this.loop.addBoundaryEdge(this.polyline);

          break;
        }

        if (!this.polylineBoundary) {
          this.endBoundaryElement();

          switch (value.getIntegerValue()) {
            case 1:
              boundaryHandler = (DXFEntityHandler) boundaryHandlers.get(Constants.ENTITY_TYPE_LINE);
              boundaryHandler.startDXFEntity();

              break;

            case 2:
              boundaryHandler = (DXFEntityHandler) boundaryHandlers.get(Constants.ENTITY_TYPE_ARC);
              boundaryHandler.startDXFEntity();

              break;

            case 3:
              boundaryHandler =
                  (DXFEntityHandler) boundaryHandlers.get(Constants.ENTITY_TYPE_ELLIPSE);
              boundaryHandler.startDXFEntity();

              break;

            case 4:
              boundaryHandler =
                  (DXFEntityHandler) boundaryHandlers.get(Constants.ENTITY_TYPE_SPLINE);
              boundaryHandler.startDXFEntity();

              break;
          }
        }

        break;

      case GROUPCODE_BOUNDAYY_LOOP_TYPE:

        // finish up last parsing
        if (!this.polylineBoundary) {
          this.endBoundaryElement();
        }

        // a new loop starts
        this.loop = new HatchBoundaryLoop();
        this.hatch.addBoundaryLoop(loop);

        // set the flags
        this.polylineBoundary = false;
        this.parseBoundary = true;

        this.loop.setBoundaryPathTypeFlag(value.getIntegerValue());

        break;

      case GROUPCODE_NAME:
        this.hatch.setName(value.getValue());

        break;

      case GROUPCODE_START_X:
        if (parseBoundary) {
          parseBoundary(groupCode, value);
        } else {
          this.hatch.getElevationPoint().setX(value.getDoubleValue());
        }

        break;

      case GROUPCODE_START_Y:
        if (parseBoundary) {
          parseBoundary(groupCode, value);
        } else {
          this.hatch.getElevationPoint().setY(value.getDoubleValue());
        }

        break;

      case GROUPCODE_START_Z:
        if (parseBoundary) {
          parseBoundary(groupCode, value);
        } else {
          this.hatch.getElevationPoint().setZ(value.getDoubleValue());
        }

        break;

      case GROUPCODE_HATCH_STYLE:
        this.parseBoundary = false;
        this.endBoundaryElement();
        this.hatch.setHatchStyle(value.getIntegerValue());

        // This should be the end of a boundary entity
        break;

      case GROUPCODE_PATTERN_TYPE:
        this.hatch.setPatternType(value.getIntegerValue());
        break;

      case GROUPCODE_PATTERN_ANGLE:
        this.hatch.setPatternAngle(value.getDoubleValue());
        break;

      case GROUPCODE_PATTERN_LINE_ANGLE:
        // set the previus parsed line data
        this.parseBoundary = false;
        this.linePattern = new HatchLineFamily();
        this.pattern.addLineFamily(this.linePattern);

        this.linePattern.setRotationAngle(value.getDoubleValue());
        this.count = 0;

        break;

      case GROUPCODE_PATTERN_BASE_X:
        this.linePattern.setBaseX(value.getDoubleValue());

        break;

      case GROUPCODE_PATTERN_BASE_Y:
        this.linePattern.setBaseY(value.getDoubleValue());

        break;

      case GROUPCODE_PATTERN_OFFSET_X:
        this.linePattern.setOffsetX(value.getDoubleValue());

        break;

      case GROUPCODE_PATTERN_OFFSET_Y:
        this.linePattern.setOffsetY(value.getDoubleValue());

        break;

      case GROUPCODE_PATTERN_LINE_COUNT:
        this.parameters = new double[value.getIntegerValue()];
        this.linePattern.setPattern(this.parameters);

        break;

      case GROUPCODE_PATTERN_LINE_TYPE_DATA:
        this.parameters[this.count] = value.getDoubleValue();
        this.count++;

        break;

      case GROUPCODE_PATTERN_SCALE:
        this.hatch.setPatternScale(value.getDoubleValue());

        break;

      default:
        if (parseBoundary) {
          parseBoundary(groupCode, value);
        } else {
          super.parseCommonProperty(groupCode, value, this.hatch);
        }
    }

    this.lastGroupCode = groupCode;
  }

  protected void parsePolylineBoundary(int groupCode, DXFValue value) {
    switch (groupCode) {
      case GROUPCODE_START_X:
        this.vertex = new Vertex();
        this.polyline.addVertex(vertex);
        this.vertex.getPoint().setX(value.getDoubleValue());

        break;

      case GROUPCODE_START_Y:
        this.vertex.getPoint().setY(value.getDoubleValue());

        break;

      case GROUPCODE_START_Z:
        this.vertex.getPoint().setZ(value.getDoubleValue());

        break;

      case DXFPolylineHandler.VERTEX_BULGE:
        this.vertex.setBulge(value.getDoubleValue());

        break;

      case 73:
        this.polyline.setFlags(1);

        break;
    }
  }

  @Override
  public void setDocument(DraftDocument doc) {
    super.setDocument(doc);

    Iterator i = this.boundaryHandlers.values().iterator();

    while (i.hasNext()) {
      DXFEntityHandler handler = (DXFEntityHandler) i.next();
      handler.setDocument(doc);
    }
  }

  @Override
  public void startDXFEntity() {
    this.hatch = new Hatch();
    this.pattern = new HatchPattern();
    this.hatch.setDocument(doc);

    this.pattern.setHatch(this.hatch);
    this.doc.addHatchPattern(pattern);

    // setup the flags
    this.parseBoundary = false;
    this.polylineBoundary = false;
    boundaryHandler = null;
  }
}
