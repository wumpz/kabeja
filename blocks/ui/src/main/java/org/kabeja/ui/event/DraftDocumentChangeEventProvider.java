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

package org.kabeja.ui.event;

/**
 * This component will fire the changed DraftDocument to all registrated Listeners to all listeners.
 *
 * @author simon
 */
public interface DraftDocumentChangeEventProvider {
  public static final String SERVICE = DraftDocumentChangeEventProvider.class.getName();

  public void addDraftDocumentChangeListener(DraftDocumentChangeListener listener);

  public void removeDraftDocumentChangeListener(DraftDocumentChangeListener listener);
}
