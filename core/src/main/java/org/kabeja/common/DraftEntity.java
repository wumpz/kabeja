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
package org.kabeja.common;

import org.kabeja.DraftDocument;
import org.kabeja.math.Bounds;
import org.kabeja.math.Extrusion;


public interface DraftEntity {

	public abstract long getID();

	public abstract Type<?> getType();

	public abstract Bounds getBounds();

	public abstract void setColor(int i);

	public abstract boolean isVisibile();

	public abstract void setVisibile(boolean b);

	public abstract boolean isModelSpace();

	public abstract int getColor();

	public abstract byte[] getColorRGB();

	public abstract int getLineWeight();

	public abstract DraftDocument getDocument();

	public abstract double getLinetypeScaleFactor();

	public abstract boolean isOmitLineType();

	public abstract boolean hasLineType();

	public abstract Layer getLayer();

	public abstract LineType getLineType();

	public abstract Extrusion getExtrusion();

	public abstract long getOwnerID();

	public abstract int getFlags();

	public abstract void setDocument(DraftDocument doc);

	public abstract void setLayer(Layer layer);

	public abstract double getLength();

	/**
	 * Ensures that the DraftEntity's coordinates are in WCS rather than OCS.
	 * Should only ever be called on completely built entities, i.e. after
	 * the entity's group has benn parsed entirely.
	 *
	 * Implementing this method is necessary only for planar entities
	 * (circle, arc, solid, trace,text, attrib, attdef, shape, insert, 2Dpolyline,
	 * 2D vertex, lwpolyline, hatch, image),
	 * since their coordinates get stored in the OCS.
	 *
	 * In this case the method does not simply translate the coordinates, but
	 * "reverts" the arbitrary axis algorithm's effects. This means that an Entity
	 * with Extrusion != (0,0,1), that might have appeared "flipped" without the
	 * transformation, will be "unflipped".
	 *
	 * 3D-Entities
	 * (line, point, 3dface, 3Dpolyline, 3D vertex, 3D mesh, 3D meshvertex)
	 * can leave this method's body empty, since their coordinates are already
	 * in WCS. For them this method should do nothing.
	 *
	 * @return the DraftEntity with its coordinates in WCS
	 */
	public abstract void toWcs();

}
