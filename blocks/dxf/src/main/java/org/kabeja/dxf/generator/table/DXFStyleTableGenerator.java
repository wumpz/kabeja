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

public class DXFStyleTableGenerator implements DXFTableGenerator {

	protected String tableID;

	public String getTableType() {
		return Constants.TABLE_KEY_STYLE;
	}

	public void output(DraftDocument doc, DXFOutput output, DXFGenerationContext context, DXFProfile type) throws GenerationException {

		if (type.hasDXFType(Constants.TABLE_KEY_STYLE)) {
			DXFType tableType = type.getDXFType(Constants.TABLE_KEY_STYLE);
			this.tableID = Utils.generateNewID(doc);
			for (DXFSubType subType : tableType.getDXFSubTypes()) {
				if (subType.getName().equals("AcDbStyle")) {
					int[] groupCodes = subType.getGroupCodes();
					for (Style style : doc.getStyles()) {
						for (int i = 0; i < groupCodes.length; i++) {
							outputStyleEntry(style, groupCodes[i], output);
						}
					}
				}

			}
		}

	}

	protected void outputStyleEntry(Style style, int groupCode, DXFOutput out) throws GenerationException {
		switch (groupCode) {
			case 0:
				out.output(0, Constants.TABLE_KEY_STYLE);
				break;
			case 2:
				out.output(2, style.getName());
				break;
			case 3:
				out.output(3, style.getFontFile());
				break;
		}
	}
}
