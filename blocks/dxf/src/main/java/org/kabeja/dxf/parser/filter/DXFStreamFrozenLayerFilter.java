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

package org.kabeja.dxf.parser.filter;

import org.kabeja.DraftDocument;
import org.kabeja.common.Layer;
import org.kabeja.parser.ParseException;

public class DXFStreamFrozenLayerFilter extends DXFStreamLayerFilter {
  private final DraftDocument doc;

  private boolean layersInitialized = false;

  public DXFStreamFrozenLayerFilter(DraftDocument doc) {
    this.doc = doc;
  }

  @Override
  protected void startEntity(String type) throws ParseException {
    super.startEntity(type);

    if (!layersInitialized) {
      initializeLayers();
      layersInitialized = true;
    }
  }

  private void initializeLayers() {
    for (Layer layer : doc.getLayers()) {
      if (layer.isFrozen()) {
        exclude.add(layer.getName());
      }
    }
  }
}
