package org.kabeja.dxf.generator.table;

import org.kabeja.DraftDocument;
import org.kabeja.common.Style;
import org.kabeja.dxf.generator.DXFGenerationContext;
import org.kabeja.dxf.generator.DXFOutput;
import org.kabeja.dxf.generator.DXFTableGenerator;
import org.kabeja.dxf.generator.conf.DXFProfile;
import org.kabeja.dxf.generator.conf.DXFSubType;
import org.kabeja.dxf.generator.conf.DXFType;
import org.kabeja.entities.util.Utils;
import org.kabeja.io.GenerationException;
import org.kabeja.util.Constants;

public class DXFAppIdTableGenerator implements DXFTableGenerator {

    protected String tableID;

    public String getTableType() {
        return Constants.TABLE_KEY_APPID;
    }

    public void output(DraftDocument doc, DXFOutput output, DXFGenerationContext context, DXFProfile type) throws GenerationException {
        if (type.hasDXFType(Constants.TABLE_KEY_APPID)) {
            DXFType tableType = type.getDXFType(Constants.TABLE_KEY_APPID);
            this.tableID = Utils.generateNewID(doc);
            for (DXFSubType subType : tableType.getDXFSubTypes()) {
                if (subType.getName().equals("APPID")) {
                    int[] groupCodes = subType.getGroupCodes();
                    for (String appid : doc.appIDs()) {
                        for (int i = 0; i < groupCodes.length; i++) {
                            outputStyleEntry(appid, groupCodes[i], output, null);
                        }
                    }
                }

            }
        }

    }

    protected void outputStyleEntry(String appid, int groupCode, DXFOutput out, String newId) throws GenerationException {
        switch (groupCode) {
            case 0:
                out.output(0, Constants.TABLE_KEY_APPID);
                break;
            case 2:
                out.output(2, appid);
                break;
            case 100:
                //out.output(100, "AcDbSymbolTableRecord");
                out.output(100, "AcDbRegAppTableRecord");
                break;
//            case 5:
//                out.output(5, newId);
//                break;
            case 70:
                out.output(70, "0");
                break;
        }
    }
}
