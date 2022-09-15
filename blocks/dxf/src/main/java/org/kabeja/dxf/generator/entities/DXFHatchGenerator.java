/** *****************************************************************************
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
 ***************************************************************************** */
package org.kabeja.dxf.generator.entities;

import org.kabeja.common.DraftEntity;
import org.kabeja.dxf.generator.DXFGenerationContext;
import org.kabeja.dxf.generator.DXFOutput;
import org.kabeja.dxf.generator.conf.DXFSubType;
import org.kabeja.entities.Hatch;
import org.kabeja.entities.Vertex;
import org.kabeja.entities.util.HatchBoundaryLoop;
import org.kabeja.io.GenerationException;
import org.kabeja.util.Constants;

public class DXFHatchGenerator extends AbstractDXFEntityGenerator {

    public String getDXFEntityType() {
        return Constants.ENTITY_TYPE_HATCH;
    }

    @Override
    protected void generateSubType(DXFSubType subtype, DraftEntity entity, DXFOutput output, DXFGenerationContext context) throws GenerationException {
        Hatch hatch = (Hatch) entity;
        if (subtype.getName().equals(Constants.SUBCLASS_MARKER_ENTITY_HATCH)) {
            for (int groupCode : subtype.getGroupCodes()) {
                switch (groupCode) {
                    case 10:
                        output.output(10, 0);
                        break;
                    case 20:
                        output.output(20, 0);
                        break;
                    case 30:
                        output.output(30, hatch.getElevationPoint().getZ());
                        break;
                    case 220:
                        output.output(220, 0);
                        break;
                    case 230:
                        output.output(230, 0);
                        break;
                    case 240:
                        output.output(240, 1);
                        break;
                    case 2:
                        output.output(2, hatch.getHatchPatternID());
                        break;
                    case 70:
                        output.output(70, hatch.isSolid() ? 1 : 0);
                        break;
                    case 71:
                        output.output(71, hatch.getAssociativityFlag());
                        break;
                    case 91:
                        output.output(91, hatch.getBoundaryPathCount());
                        System.out.println("using profile " + context.getAttribute(DXFGenerationContext.ATTRIBUTE_PROFILE));
                        for (HatchBoundaryLoop boundary : hatch.getBoundaryLoops()) {
                            System.out.println("edge output not yet implemented");
                        }
                        break;
                    case 75:
                        output.output(75, hatch.getHatchStyle());
                        break;
                    case 76:
                        output.output(76, hatch.getPatternType());
                        break;
                    case 52:
                        if (!hatch.isSolid()) {
                            output.output(52, hatch.getPatternAngle());
                        }
                        break;
                    case 41:
                        if (!hatch.isSolid()) {
                            output.output(41, hatch.getPatternScaleSpacing());
                        }
                        break;
                    case 77:
                        //Hatch pattern double flag
                        break;
                    case 78:
                        // Number of pattern definition lines
                        break;
                    case 450:
                        output.output(450, 0);
                        break;
                    case 451:
                        output.output(451, 0);
                        break;
                    case 452:
                        // two color gradient or single color grandient
                        break;
                    case 453:
                        // number of colors
                        output.output(453, 0);
                        break;
                    case 460:
                        output.output(460, 0.0);
                        break;
                    case 462:
                        output.output(462, 0);
                        break;
                    case 470:
                        output.output(470, "");
                        break;
                    default:
                        super.outputCommonGroupCode(groupCode, hatch, output);
                }
            }
        }
    }
}
