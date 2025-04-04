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
package org.kabeja.tools;

import java.io.File;

public class IOUtils {

  public static String getFileExtension(File f) {
    String ext = f.getAbsolutePath();
    int index = ext.lastIndexOf(".");

    if ((index + 1) < ext.length()) {
      return ext.substring(index + 1);
    }

    return "";
  }
}
