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
import org.kabeja.common.Block;
import org.kabeja.common.DraftEntity;
import org.kabeja.common.LineWidth;
import org.kabeja.entities.Dimension;
import org.kabeja.math.Point3D;
import org.kabeja.math.TransformContext;
import org.kabeja.svg.SVGConstants;
import org.kabeja.svg.SVGContext;
import org.kabeja.svg.SVGUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class SVGDimensionGenerator extends AbstractSVGSAXGenerator {
  public void toSAX(
      ContentHandler handler, Map svgContext, DraftEntity entity, TransformContext transformContext)
      throws SAXException {
    Dimension dimension = (Dimension) entity;

    Block block = dimension.getDocument().getBlock(dimension.getDimensionBlock());
    if (block != null) {

      AttributesImpl attr = new AttributesImpl();
      StringBuffer buf = new StringBuffer();

      buf.append("translate(");
      buf.append((dimension.getInsertPoint().getX()));
      buf.append(SVGConstants.SVG_ATTRIBUTE_PATH_PLACEHOLDER);
      buf.append((dimension.getInsertPoint().getY()));
      buf.append(")");

      Point3D referencePoint = block.getReferencePoint();

      if ((referencePoint.getX() != 0.0) || (referencePoint.getY() != 0.0)) {
        buf.append(" translate(");
        buf.append(SVGUtils.formatNumberAttribute(referencePoint.getX()));
        buf.append(SVGConstants.SVG_ATTRIBUTE_PATH_PLACEHOLDER);
        buf.append(SVGUtils.formatNumberAttribute(referencePoint.getY()));
        buf.append(")");
      }

      SVGUtils.addAttribute(attr, "transform", buf.toString());

      super.setCommonAttributes(attr, svgContext, dimension);

      if (svgContext.containsKey(SVGContext.LAYER_STROKE_WIDTH)) {
        LineWidth lw = (LineWidth) svgContext.get(SVGContext.LAYER_STROKE_WIDTH);
        SVGUtils.addAttribute(
            attr, SVGConstants.SVG_ATTRIBUTE_STROKE_WITDH, SVGUtils.lineWidthToStrokeWidth(lw));
      }

      SVGUtils.startElement(handler, SVGConstants.SVG_GROUP, attr);
      attr = new AttributesImpl();

      attr.addAttribute(
          SVGConstants.XMLNS_NAMESPACE,
          "xlink",
          "xmlns:xlink",
          "CDATA",
          SVGConstants.XLINK_NAMESPACE);
      attr.addAttribute(
          SVGConstants.XLINK_NAMESPACE,
          "href",
          "xlink:href",
          "CDATA",
          "#" + SVGUtils.toValidateID(block.getID()));

      SVGUtils.emptyElement(handler, SVGConstants.SVG_USE, attr);

      SVGUtils.endElement(handler, SVGConstants.SVG_GROUP);
    }

    // else if (dimType == TYPE_LINEAR || dimType == 32 || dimType == 160) {
    // LinearDimensionOutputter out = new LinearDimensionOutputter(
    // this);
    // out.output(handler);
    // }
  }
}
