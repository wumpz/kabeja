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
 * Created on 17.10.2005
 *
 */
package org.kabeja.entities;

import org.kabeja.common.DraftEntity;
import org.kabeja.common.Type;
import org.kabeja.math.Bounds;

/**
 * @author <a href="mailto:simon.mieth@gmx.de>Simon Mieth</a>
 */
public class XLine extends Ray {
  @Override
  public Bounds getBounds() {
    // the xline is a infinite straight line
    // so we omit the bounds
    Bounds bounds = new Bounds();
    bounds.setValid(false);

    return bounds;
  }

  @Override
  public Type<? extends DraftEntity> getType() {
    return Type.TYPE_XLINE;
  }

  /** Does not need to be implemented, as XLine is a non-planar entity. */
  @Override
  public void toWcs() {
    super.toWcs();
  }
}
