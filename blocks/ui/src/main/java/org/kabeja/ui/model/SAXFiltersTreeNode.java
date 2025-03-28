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

package org.kabeja.ui.model;

import java.util.Iterator;
import javax.swing.tree.TreeNode;

public class SAXFiltersTreeNode extends AbstractProcessingTreeNode {
  protected static final String LABEL = "SAXFilters";

  public SAXFiltersTreeNode(TreeNode parent) {
    super(parent, LABEL);
  }

  protected void initializeChildren() {
    Iterator i = this.manager.getSAXFilters().keySet().iterator();

    while (i.hasNext()) {
      String key = (String) i.next();
      System.out.println("UI SAXFilter:" + key + " filter:" + this.manager.getSAXFilter(key));
      this.addChild(new SAXFilterTreeNode(this, this.manager.getSAXFilter(key), key));
    }
  }

  public boolean getAllowsChildren() {
    return true;
  }

  public boolean isLeaf() {
    return false;
  }
}
