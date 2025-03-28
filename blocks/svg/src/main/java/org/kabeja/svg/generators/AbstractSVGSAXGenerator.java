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

package org.kabeja.svg.generators;

import java.util.Map;
import org.kabeja.DraftDocument;
import org.kabeja.common.Color;
import org.kabeja.common.DraftEntity;
import org.kabeja.common.LineType;
import org.kabeja.svg.SVGConstants;
import org.kabeja.svg.SVGContext;
import org.kabeja.svg.SVGSAXGenerator;
import org.kabeja.svg.SVGUtils;
import org.xml.sax.helpers.AttributesImpl;

public abstract class AbstractSVGSAXGenerator implements SVGSAXGenerator {

  public void setCommonAttributes(AttributesImpl atts, Map context, DraftEntity entity) {

    setVisibilityAttribute(atts, entity);
    setXMLIDAttribute(atts, entity);
    setColorAttribute(atts, entity);
    setStrokeAttribute(atts, context, entity);
  }

  public void setCommonTextAttributes(AttributesImpl atts, Map context, DraftEntity entity) {
    setVisibilityAttribute(atts, entity);
    setXMLIDAttribute(atts, entity);
    setColorAttribute(atts, entity);
    SVGUtils.addAttribute(
        atts, SVGConstants.SVG_ATTRIBUTE_STROKE, SVGConstants.SVG_ATTRIBUTE_FILL_VALUE_NONE);
    SVGUtils.addAttribute(
        atts, SVGConstants.SVG_ATTRIBUTE_FILL, SVGConstants.SVG_ATTRIBUTE_VALUE_CURRENTCOLOR);
  }

  protected void setVisibilityAttribute(AttributesImpl atts, DraftEntity entity) {
    // a negative color indicates the layer is off
    if (!entity.isVisibile()) {
      // we calculate the bounds self so they must not
      // rendered from the SVG-Renderer.
      // If they should be in the rendering-tree change
      // this to visible=hidden
      SVGUtils.addAttribute(
          atts, SVGConstants.SVG_ATTRIBUTE_DISPLAY, SVGConstants.SVG_ATTRIBUTE_DISPLAY_VALUE_NONE);
    }
  }

  protected void setColorAttribute(AttributesImpl atts, DraftEntity entity) {
    // color 256 indicates color by layer
    int color = entity.getColor();

    if ((color != 0) && (color != 256)) {
      // check RGB color first
      byte[] b = entity.getColorRGB();
      if (b.length == 3) {
        SVGUtils.addAttribute(atts, SVGConstants.SVG_ATTRIBUTE_COLOR, Color.getRGBString(b));
      } else {

        SVGUtils.addAttribute(atts, SVGConstants.SVG_ATTRIBUTE_COLOR, Color.getRGBString(color));
      }
    }
  }

  protected void setXMLIDAttribute(AttributesImpl atts, DraftEntity entity) {

    SVGUtils.addAttribute(atts, SVGConstants.XML_ID, SVGUtils.toValidateID(entity.getID()));
  }

  protected void setStrokeAttribute(AttributesImpl atts, Map context, DraftEntity entity) {

    SVGUtils.addAttribute(
        atts, SVGConstants.SVG_ATTRIBUTE_STROKE, SVGConstants.SVG_ATTRIBUTE_VALUE_CURRENTCOLOR);

    if ((entity.getLineWeight() > 0)
        && !context.containsKey(SVGContext.DRAFT_STROKE_WIDTH_IGNORE)) {
      SVGUtils.addAttribute(
          atts,
          SVGConstants.SVG_ATTRIBUTE_STROKE_WITDH,
          SVGUtils.lineWeightToStrokeWidth(entity.getLineWeight()));
    }

    DraftDocument doc = entity.getDocument();

    double gscale = doc.getHeader().getLinetypeScale();

    if (entity.hasLineType() && !entity.isOmitLineType()) {

      gscale = gscale * entity.getLinetypeScaleFactor();
      SVGUtils.addStrokeDashArrayAttribute(atts, entity.getLineType(), gscale);

    } else if (!entity.isOmitLineType()) {
      // get the line type from layer
      LineType ltype = entity.getLayer().getLineType();

      if (ltype != null) {
        gscale = gscale * entity.getLinetypeScaleFactor();
        SVGUtils.addStrokeDashArrayAttribute(
            atts, ltype, (entity.getLinetypeScaleFactor() * gscale));
      } else if (entity.isOmitLineType()) {
        SVGUtils.addAttribute(atts, SVGConstants.SVG_ATTRIBUTE_STROKE_DASHARRAY, "");
      }
    }
  }
}
