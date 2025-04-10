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

package org.kabeja.batik.tools;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.image.PNGTranscoder;

/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth</a>
 */
public class SAXPNGSerializer extends AbstractSAXSerializer {
  /* (non-Javadoc)
   * @see org.kabeja.batik.tools.AbstractSAXSerializer#createTranscoder()
   */
  protected Transcoder createTranscoder() {
    return new PNGTranscoder();
  }

  /* (non-Javadoc)
   * @see org.kabeja.xml.SAXSerializer#getMimeType()
   */
  public String getMimeType() {
    // TODO Auto-generated method stub
    return MIME_TYPE_PNG;
  }

  /* (non-Javadoc)
   * @see org.kabeja.xml.SAXSerializer#getSuffix()
   */
  public String getSuffix() {
    // TODO Auto-generated method stub
    return SUFFIX_PNG;
  }
}
