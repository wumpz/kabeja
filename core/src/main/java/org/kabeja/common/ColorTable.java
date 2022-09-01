/*
 * Copyright 2022 tw.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kabeja.common;

import static org.kabeja.common.Color.KABEJA_DEFAULT_COLOR;

/**
 * Holds color table values together. The class Color is just a static access point.
 */
public class ColorTable {

    private final int[][] rgbs;

    public ColorTable(int[][] colortable) {
        this.rgbs = colortable;
    }

    public String getRGBString(int dxfColorCode) {
        if ((dxfColorCode < 0) || (dxfColorCode >= rgbs.length)) {
            // default is white
            dxfColorCode = 7;
        }

        return String.format("rgb(%d,%d,%d)", rgbs[dxfColorCode][0], rgbs[dxfColorCode][1], rgbs[dxfColorCode][2]);
    }

    public String getRGBHexString(int dxfColorCode) {
        if ((dxfColorCode < 0) || (dxfColorCode >= rgbs.length)) {
            // default is white
            dxfColorCode = 7;
        }

        return String.format("%02x%02x%02x", rgbs[dxfColorCode][0], rgbs[dxfColorCode][1], rgbs[dxfColorCode][2]);
    }

    public int findClosestColorIndex(int r, int g, int b) {
        int minDistanceColorIdx = 0;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < rgbs.length; i++) {
            int distance = (rgbs[i][0] - r) * (rgbs[i][0] - r)
                           + (rgbs[i][1] - g) * (rgbs[i][1] - g)
                           + (rgbs[i][2] - b) * (rgbs[i][2] - b);

            if (distance == 0) {
                return i;
            }

            if (distance < minDistance) {
                minDistance = distance;
                minDistanceColorIdx = i;
            }
        }

        return minDistanceColorIdx;
    }
}
