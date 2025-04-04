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
package org.kabeja.dxf.generator;

import org.kabeja.dxf.generator.conf.DXFProfile;

public interface DXFGeneratorManager {

  public DXFEntityGenerator getDXFEntityGenerator(String entityType);

  public boolean hasDXFEntityGenerator(String entityType);

  public DXFSectionGenerator getDXFSectionGenerator(String section);

  public boolean hasDXFSectionGenerator(String section);

  public boolean hasDXFTableGenerator(String tableType);

  public DXFTableGenerator getDXFTableGenerator(String tableType);

  public DXFProfile getDXFProfile(String name);

  public boolean hasDXFProfile(String name);
}
