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
 * Created on 25.11.2008
 *
 */
package org.kabeja.dxf.generator.section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kabeja.DraftDocument;
import org.kabeja.common.Header;
import org.kabeja.common.Variable;
import org.kabeja.dxf.generator.DXFGenerationContext;
import org.kabeja.dxf.generator.DXFOutput;
import org.kabeja.dxf.generator.DXFSectionGenerator;
import org.kabeja.dxf.generator.conf.DXFProfile;
import org.kabeja.io.GenerationException;
import org.kabeja.util.Constants;

public class DXFHeaderSectionGenerator implements DXFSectionGenerator {

  public static final String PROPERTY_HEADER_VARIABLE_LIST = "header.variables";

  protected Set omit = new HashSet();
  protected Map predefined = new HashMap();

  protected String[] variableList =
      new String[] {
        "$ACADVER",
        "$ATTMODE",
        "$FILLMODE",
        "$LTSCALE",
        "$LWDISPLAY",
        "$PDMODE",
        "$PDSIZE",
        "$PSLTSCALE"
      };

  protected void outputDXFVariable(Variable var, DXFOutput out) throws GenerationException {
    out.output(9, var.getName());
    Iterator keyIterator = var.getValueKeyIterator();
    while (keyIterator.hasNext()) {
      String key = (String) keyIterator.next();
      out.output(Integer.parseInt(key), ((String) var.getValue(key)).trim());
    }
  }

  public void setProperties(Map properties) {
    if (properties.containsKey(PROPERTY_HEADER_VARIABLE_LIST)) {
      variableList = ((String) properties.get(PROPERTY_HEADER_VARIABLE_LIST)).split(",");
    }
  }

  protected void prepare() {
    Variable var = new Variable("$ACADVER");
    var.setValue("1", "AC1009");
    predefined.put(var.getName(), var);
  }

  @Override
  public String getSectionName() {

    return Constants.SECTION_HEADER;
  }

  @Override
  public void generate(
      DXFOutput output, DraftDocument doc, DXFGenerationContext dxfContext, DXFProfile profile)
      throws GenerationException {

    prepare();
    Header header = doc.getHeader();
    ArrayList<String> varList = new ArrayList<>();

    // filter out the variable to omit
    for (String variableList1 : variableList) {
      if (!omit.contains(variableList1)) {
        varList.add(variableList1);
      }
    }

    final List<String> headerVariables =
        (List<String>) dxfContext.getAttribute(PROPERTY_HEADER_VARIABLE_LIST);

    if (headerVariables != null) {
      for (String o : headerVariables) {
        if (!omit.contains(o)) {
          varList.add(o);
        }
      }
    }

    final HashMap<String, Variable> map = new HashMap<>();

    for (String varName : varList) {
      if (header.hasVariable(varName)) {
        Variable var = header.getVariable(varName);
        map.put(varName, var);
      } else if (predefined.containsKey(varName)) {
        Variable var = (Variable) predefined.get(varName);
        map.put(varName, var);
      }
    }

    for (Variable var : map.values()) {
      outputDXFVariable(var, output);
    }
  }
}
