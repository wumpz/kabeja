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

import java.io.OutputStream;
import java.util.Map;
import org.kabeja.DraftDocument;
import org.kabeja.io.GenerationException;

/**
 * This interface describes a Generator, which will generate output the given stream.
 *
 * <h3>Lifecycle</h3>
 *
 * <ol>
 *   <li>setProperties
 *   <li>getSuffix()
 *   <li>getMimeType()
 *   <li>generate()
 * </ol>
 *
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public interface Generator {
  public void setProperties(Map<String, Object> properties);

  public String getSuffix();

  public String getMimeType();

  /**
   * Output the generation result to the given stream.
   *
   * @param doc the @see org.kabeja.DraftDocument to output
   * @param context
   * @param out
   * @throws GenerationException TODO
   */
  public void generate(DraftDocument doc, Map<String, Object> context, OutputStream out)
      throws GenerationException;
}
