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

package org.kabeja.entities;

import org.kabeja.DraftDocument;
import org.kabeja.common.DraftEntity;
import org.kabeja.common.Layer;
import org.kabeja.common.LineType;
import org.kabeja.common.Type;
import org.kabeja.entities.util.Utils;
import org.kabeja.math.Bounds;
import org.kabeja.math.Extrusion;
import org.kabeja.math.TransformContext;
import org.kabeja.tools.LazyContainer;
import org.kabeja.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:simon.mieth@gmx.de>Simon Mieth</a>
 * 
 * 
 */
public abstract class Entity implements DraftEntity {

	protected static final int LAZY_INDEX_LAYER = 0;
	protected static final int LAZY_INDEX_LINETYPE = 1;
	protected static final int LAZY_INDEX_EXTRUSION = 2;
	protected static final int LAZY_INDEX_LINETYPE_SCALE = 3;
	protected static final int LAZY_INDEX_LINEWEIGHT = 4;
	protected static final int LAZY_INDEX_TRANSPARENCY = 5;
	protected static final int LAZY_INDEX_THICKNESS = 6;
	protected static final int LAZY_INDEX_COLOR = 7;

	protected LazyContainer lazyContainer = new LazyContainer();

	protected DraftDocument doc;

	protected long id;
	protected long ownerID;

	protected int flags = 0;

	protected final static int BOOLEAN_BIT_VISIBILITY = 0;
	protected final static int BOOLEAN_BIT_BLOCKENTITY = 1;
	protected final static int BOOLEAN_BIT_MODELSPACE = 2;
	protected final static int BOOLEAN_BIT_OWN_LINETYPE = 3;
	protected int entityFlags;

	protected List<XData> xDataList = new ArrayList<>();

	public Entity() {
		init();
	}

	/**
	 * Set the owner document of the entity
	 * 
	 * @param doc
	 */

    @Override
	public void setDocument(DraftDocument doc) {
		this.doc = doc;
	}

	/**
	 * Get the owner document of the entity
	 * 
	 * @return
	 */

    @Override
	public DraftDocument getDocument() {
		return this.doc;
	}

	/**
	 * Returns the parent layer of the entity
	 * 
	 * @return
	 */
    @Override
	public Layer getLayer() {
		if (this.lazyContainer.contains(LAZY_INDEX_LAYER)) {
			return ((Layer) this.lazyContainer.get(LAZY_INDEX_LAYER));
		}
		return this.doc.getRootLayer();
	}

	/**
	 * Set the parent layer of the entity
	 * 
	 * @param layer
	 */
    @Override
	public void setLayer(Layer layer) {
		this.lazyContainer.set(layer, LAZY_INDEX_LAYER);
	}

    @Override
	public abstract Bounds getBounds();

    @Override
	public LineType getLineType() {
		if (this.lazyContainer.contains(LAZY_INDEX_LINETYPE)) {
			return (LineType) this.lazyContainer.get(LAZY_INDEX_LINETYPE);
		}
		return this.getLayer().getLineType();
	}

	public void setLineType(LineType ltype) {
		this.lazyContainer.set(ltype, LAZY_INDEX_LINETYPE);
	}

    @Override
	public boolean hasLineType() {
		return this.lazyContainer.contains(LAZY_INDEX_LINETYPE);
	}

	/**
	 * @return Returns the visibile.
	 */
    @Override
	public boolean isVisibile() {
		return Utils.isBitEnabled(this.entityFlags, BOOLEAN_BIT_VISIBILITY);

	}

	/**
	 * @param visible
	 *            The visibile to set.
	 */
    @Override
	public void setVisibile(boolean visible) {
		this.entityFlags = Utils.setBit(this.entityFlags,
				BOOLEAN_BIT_VISIBILITY, visible);
	}

	/**
	 * @return Returns the flags.
	 */
    @Override
	public int getFlags() {
		return flags;
	}

	/**
	 * @param flags
	 *            The flags to set.
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}

	public void setBlockEntity(boolean b) {
		this.entityFlags = Utils.setBit(this.entityFlags,
				BOOLEAN_BIT_BLOCKENTITY, b);
	}

	public boolean isBlockEntity() {
		return Utils.isBitEnabled(this.entityFlags, BOOLEAN_BIT_BLOCKENTITY);
	}

	public void setExtrusion(Extrusion extrusion) {
		this.lazyContainer.set(extrusion, LAZY_INDEX_EXTRUSION);
	}

    @Override
	public Extrusion getExtrusion() {

		if (this.lazyContainer.contains(LAZY_INDEX_EXTRUSION)) {
			return (Extrusion) this.lazyContainer.get(LAZY_INDEX_EXTRUSION);
		}

		return new Extrusion() {
			@Override
			public void setX(double x) {
				if (x != 0.0) {
					this.addToContainer();
					super.setX(x);
				}
			}

			@Override
			public void setY(double y) {
				if (y != 0.0) {
					this.addToContainer();
					super.setY(y);
				}
			}

			@Override
			public void setZ(double z) {
				if (z != 1.0) {
					this.addToContainer();
					super.setZ(z);
				}
			}

			private void addToContainer() {
				if (!lazyContainer.contains(LAZY_INDEX_EXTRUSION)) {
					lazyContainer.set(this, LAZY_INDEX_EXTRUSION);
				}
			}

		};

	}

    @Override
	public double getLinetypeScaleFactor() {
		if (this.lazyContainer.contains(LAZY_INDEX_LINETYPE_SCALE)) {
			return ((Double) this.lazyContainer.get(LAZY_INDEX_LINETYPE_SCALE));
		} else {
			return 1.0;
		}

	}

	public void setLinetypeScaleFactor(double linetypeScaleFactor) {
		this.lazyContainer.set(linetypeScaleFactor,
				LAZY_INDEX_LINETYPE_SCALE);

	}

    @Override
	public int getColor() {
		if (this.lazyContainer.contains(LAZY_INDEX_COLOR)) {
			int color = ((byte[]) this.lazyContainer.get(LAZY_INDEX_COLOR))[0];
			return color < 0 ? color + 256 : color;
		} else {
			return Constants.COLOR_VALUE_BY_LAYER;
		}
	}

    @Override
	public void setColor(int color) {
		if (this.lazyContainer.contains(LAZY_INDEX_COLOR)
				|| color != Constants.COLOR_VALUE_BY_LAYER) {
			this.lazyContainer.set(new byte[] { (byte) color },
					LAZY_INDEX_COLOR);
		}
	}

    @Override
	public byte[] getColorRGB() {
		if (this.lazyContainer.contains(LAZY_INDEX_COLOR)) {
			return ((byte[]) this.lazyContainer.get(LAZY_INDEX_COLOR));
		}
		return new byte[0];
	}

	public void setColorRGB(byte[] colorRGB) {
		if (this.lazyContainer.contains(LAZY_INDEX_COLOR)
				|| colorRGB.length > 0) {
			this.lazyContainer.set(colorRGB, LAZY_INDEX_COLOR);
		}

	}

    @Override
	public int getLineWeight() {
		if (this.lazyContainer.contains(LAZY_INDEX_LINEWEIGHT)) {
			return ((Integer) this.lazyContainer.get(LAZY_INDEX_LINEWEIGHT));
		}
		return 0;
	}

	public void setLineWeight(int lineWeight) {
		if (this.lazyContainer.contains(LAZY_INDEX_LINETYPE) || lineWeight != 0) {
			this.lazyContainer.set(lineWeight,
					LAZY_INDEX_LINEWEIGHT);
		}
	}

	public double getTransparency() {
		if (this.lazyContainer.contains(LAZY_INDEX_TRANSPARENCY)) {
			return ((Double) this.lazyContainer.get(LAZY_INDEX_TRANSPARENCY));
		} else {
			return 0.0;
		}
	}

	public void setTransparency(double transparency) {
		if (this.lazyContainer.contains(LAZY_INDEX_TRANSPARENCY)
				|| transparency != 0.0) {
			this.lazyContainer.set(transparency,
					LAZY_INDEX_TRANSPARENCY);
		}

	}

	public void setID(long id) {

		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kabeja.entities.EntityType#getID()
	 */

    @Override
	public long getID() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kabeja.entities.EntityType#getType()
	 */

    @Override
	public abstract Type<?> getType();

	/**
	 * The thickness reflects the height of the entity.
	 * 
	 * 
	 * @return Returns the thickness.
	 */
	public double getThickness() {
		if (this.lazyContainer.contains(LAZY_INDEX_THICKNESS)) {
			return ((Double) this.lazyContainer.get(LAZY_INDEX_THICKNESS));
		} else {
			return 0.0;
		}
	}

	/**
	 * @param thickness
	 *            The thickness /height of the entity to set.
	 */
	public void setThickness(double thickness) {
		if (this.lazyContainer.contains(LAZY_INDEX_THICKNESS)
				|| thickness != 0.0) {
			this.lazyContainer.set(thickness, LAZY_INDEX_THICKNESS);
		}

	}

    @Override
	public boolean isOmitLineType() {
		return false;
	}

	/**
	 * @return Returns the modelSpace.
	 */
    @Override
	public boolean isModelSpace() {
		return Utils.isBitEnabled(this.entityFlags, BOOLEAN_BIT_MODELSPACE);
	}

	/**
	 * @param modelSpace
	 *            The modelSpace to set.
	 */
	public void setModelSpace(boolean modelSpace) {
		this.entityFlags = Utils.setBit(this.entityFlags,
				BOOLEAN_BIT_MODELSPACE, modelSpace);
	}

	/**
	 * Returns the length of the entity or 0 if the entity has no length
	 * 
	 * @return
	 */
    @Override
	public abstract double getLength();

    @Override
	public long getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(long ownerID) {
		this.ownerID = ownerID;
	}

	protected void init() {

		// setup the default bis
		this.entityFlags = 0;
		// visibility to true
		this.entityFlags = Utils.enableBit(this.entityFlags, 0);
		// modelspace to true
		setModelSpace(true);

	}

	protected void setBit(int bit, boolean b) {
		this.entityFlags = Utils.setBit(this.entityFlags, bit, b);
	}

	protected boolean isBitEnabled(int bit) {
		return Utils.isBitEnabled(this.entityFlags, bit);
	}

	public abstract void transform(TransformContext context);

	public void addXData(String applicationName) {
		xDataList.add(new XData(applicationName));
	}

	public void setXDataString(String xDataString) {
		if (!xDataList.isEmpty()) {
			xDataList.get(xDataList.size() - 1).setxDataString(xDataString);
		}
	}

    @Override
	public List<XData> getXDataList() {
		return xDataList;
	}

	/**
	 * Ensures that the Entity's coordinates are in WCS rather than OCS.
	 * Should only ever be called on completely built entities, i.e. after
	 * the entity's group has benn parsed entirely.
	 *
	 * Overriding this method is necessary for planar entities
	 * (circle, arc, solid, trace, text, attrib, attdef, shape, insert, 2Dpolyline,
	 * 2D vertex, lwpolyline, hatch, image),
	 * since their coordinates get stored in the OCS.
	 *
	 * In this case the method does not simply translate the coordinates, but
	 * "reverts" the arbitrary axis algorithm's effects. This means that an Entity
	 * with Extrusion != (0,0,1), that might have appeared "flipped" without the
	 * transformation, will be "unflipped".
	 *
	 * 3D-Entities
	 * (line, point, 3dface, 3Dpolyline, 3D mesh, 3D meshvertex, Ellipse)
	 * do not need to override this method.
	 *
	 * @return the Entity with its coordinates in WCS
	 */
	@Override
	public void toWcs(){}
}
