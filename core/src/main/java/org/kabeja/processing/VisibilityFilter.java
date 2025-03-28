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

package org.kabeja.processing;

import java.util.Iterator;
import java.util.Map;
import org.kabeja.DraftDocument;
import org.kabeja.common.DraftEntity;
import org.kabeja.common.Layer;
import org.kabeja.common.Type;

/**
 * This postprocessor remove all invisible entities and layers.
 *
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public class VisibilityFilter extends AbstractPostProcessor {

  @Override
  public void process(DraftDocument doc, Map<String, Object> context) throws ProcessorException {
    Iterator<Layer> i = doc.getLayers().iterator();

    while (i.hasNext()) {
      Layer l = (Layer) i.next();

      if (l.isVisible()) {
        for (Type<?> type : l.getEntityTypes()) {

          Iterator<? extends DraftEntity> ei = l.getEntitiesByType(type).iterator();

          while (ei.hasNext()) {
            DraftEntity entity = (DraftEntity) ei.next();

            if (!entity.isVisibile()) {
              ei.remove();
            }
          }
        }
      } else {
        i.remove();
      }
    }
  }

  @Override
  public void setProperties(Map<String, Object> properties) {}
}
