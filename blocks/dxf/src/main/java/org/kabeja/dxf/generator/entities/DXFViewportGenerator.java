package org.kabeja.dxf.generator.entities;

import org.kabeja.DraftDocument;
import org.kabeja.dxf.generator.DXFOutput;
import org.kabeja.dxf.generator.DXFTableGenerator;
import org.kabeja.dxf.generator.conf.DXFProfile;
import org.kabeja.entities.Viewport;
import org.kabeja.io.GenerationException;
import org.kabeja.math.Bounds;
import org.kabeja.util.Constants;

public class DXFViewportGenerator implements DXFTableGenerator {
    @Override
    public void output(DraftDocument doc, DXFOutput out, org.kabeja.dxf.generator.DXFGenerationContext dxfContext, DXFProfile type) throws GenerationException {
        final int[] groupCodes = type.getDXFType(Constants.TABLE_KEY_VPORT).getDXFSubTypes().get(0).getGroupCodes();

        // workaround
        if (!doc.getViewports().isEmpty()) {
            out.output(70, 0);
            out.output(0, Constants.TABLE_KEY_VPORT);
        }

        for (final Viewport viewport : doc.getViewports()) {
            final Bounds bounds = viewport.getBounds();

            for (int groupCode : groupCodes) {
                switch (groupCode) {
                    case 2:
                        out.output(2, viewport.getViewportID());
                        break;
                    case 10: // Center point X in WCS
                        out.output(10, 0);
                        break;
                    case 11:
                        out.output(11, bounds.getMaximumX());
                        break;
                    case 12:
                        out.output(12, viewport.getCenterPoint().getX());
                        break;
                    case 13:
                        out.output(13, viewport.getSnapBasePoint().getX());
                        break;
                    case 14:
                        out.output(14, viewport.getSnapSpacingPoint().getX());
                        break;
                    case 15:
                        out.output(15, viewport.getGridSpacingPoint().getX());
                        break;
                    case 16:
                        out.output(16, viewport.getViewDirectionVector().getX());
                        break;
                    case 17:
                        out.output(17, viewport.getViewTargetPoint().getX());
                        break;
                    case 20: // Center point Y in WCS
                        out.output(20, 0);
                        break;
                    case 21:
                        out.output(21, bounds.getMaximumY());
                        break;
                    case 22:
                        out.output(22, viewport.getCenterPoint().getY());
                        break;
                    case 23:
                        out.output(23, viewport.getSnapBasePoint().getY());
                        break;
                    case 24:
                        out.output(24, viewport.getSnapSpacingPoint().getY());
                        break;
                    case 25:
                        out.output(25, viewport.getGridSpacingPoint().getY());
                        break;
                    case 26:
                        out.output(26, viewport.getViewDirectionVector().getY());
                        break;
                    case 27:
                        out.output(27, viewport.getViewTargetPoint().getY());
                        break;
                    case 36:
                        out.output(36, viewport.getViewDirectionVector().getZ());
                        break;
                    case 37:
                        out.output(37, viewport.getViewTargetPoint().getZ());
                        break;
                    case 40:
                        out.output(40, viewport.getWidth());
                        break;
                    case 41:
                        out.output(41, viewport.getHeight());
                        break;
                    case 42:
                        out.output(42, viewport.getLensLength());
                        break;
                    case 43:
                        out.output(43, viewport.getFrontClippingPlane());
                        break;
                    case 44:
                        out.output(44, viewport.getBackClippingPlane());
                        break;
                    case 50:
                        out.output(50, viewport.getSnapAngle());
                        break;
                    case 51:
                        out.output(51, viewport.getTwistAngle());
                        break;
                    case 70:
                        out.output(70, viewport.getFlags());
                        break;
                    case 71:
                        out.output(71, viewport.getUcsType());
                        break;
                    case 72:
                        out.output(72, viewport.getCircleZoom());
                        break;
                    case 73: // ???
                        out.output(73, 1);
                        break;
                    case 74: // Display UCS icon at UCS origin flag
                        out.output(74, 4);
                        break;
                    case 75: // ???
                        out.output(75, 0);
                        break;
                    case 76: // ???
                        out.output(76, 1);
                        break;
                    case 77: // ???
                        out.output(77, 0);
                        break;
                    case 78: // ???
                        out.output(78, 0);
                        break;
                }
            }
        }
    }
}
