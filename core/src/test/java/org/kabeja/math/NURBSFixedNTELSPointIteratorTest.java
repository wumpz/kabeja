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
package org.kabeja.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.kabeja.entities.Polyline;
import org.kabeja.entities.Vertex;

public class NURBSFixedNTELSPointIteratorTest {

  public static final double DELTA = 1.0E-5;

  @Test
  public void weirdSplineToPolyline() {
    Point3D[] points =
        new Point3D[] {
          new Point3D(29.11592918662407, 22.78831058877996, 0),
              new Point3D(29.11592918662407, 22.78831058877996, 0),
          new Point3D(29.11592918662407, 22.78831058877996, 0),
              new Point3D(29.11592918662407, 22.78831058877996, 0),
          new Point3D(29.11892918665015, 22.79181058877976, 0),
              new Point3D(29.11392918664549, 22.78481058878015, 0),
          new Point3D(29.11592918662407, 22.78831058877996, 0)
        };
    double[] knots =
        new double[] {
          -452704.293346133,
          -452704.293346133,
          -452704.293346133,
          -452704.293346133,
          -452704.2933461327,
          -452704.2933461327,
          -452704.2933461327,
          -452704.2933461324,
          -452704.2933461324,
          -452704.2933461324,
          -452704.2933461324
        };
    double[] w = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};

    Polyline p = new Polyline();
    NURBS n = new NURBS(points, knots, w, 3);
    n.setClosed(true);
    NURBSFixedNTELSPointIterator it = new NURBSFixedNTELSPointIterator(n, 30);

    while (it.hasNext()) {
      p.addVertex(new Vertex(it.next()));
    }
    // the test is successful, as we were able to get "all the points" from the spline without an
    // AssertionError
  }

  @Test
  public void nurbsCornerToPolyline() {
    Point3D[] points =
        new Point3D[] {
          new Point3D(0, 0, 0), new Point3D(0.5, 0.0, 0.0),
          new Point3D(1.0, 0.0, 0), new Point3D(1.0, 0.5, 0),
          new Point3D(1.0, 1.0, 0)
        };
    double[] knots = new double[] {0.0, 0.0, 0.0, 1.0, 1.0, 2.0, 2.0, 2.0};
    double[] w = new double[] {1.0, 1.0, 1.0, 1.0, 1.0};

    NURBS n = new NURBS(points, knots, w, 2);
    Polyline p = new Polyline();

    NURBSFixedNTELSPointIterator it = new NURBSFixedNTELSPointIterator(n, 30);

    while (it.hasNext()) {
      p.addVertex(new Vertex(it.next()));
    }

    Point3D cornerPoint = n.getPointAt(1.0);
    int verticesOnCorner = 0;
    for (int i = 0; i < p.getVertexCount(); i++) {
      if (cornerPoint.equals(p.getVertex(i).getPoint())) {
        verticesOnCorner++;
      }
    }
    assertEquals("There needs to be exactly one Point on the corner", 1, verticesOnCorner);
  }

  @Test
  public void nurbsCornerToPolyline2() {
    Point3D[] points =
        new Point3D[] {
          new Point3D(0, 0, 0), new Point3D(0.5, 0.0, 0.0),
          new Point3D(1.0, 0.0, 0), new Point3D(1.5, 0.0, 0),
          new Point3D(1.5, 0.5, 0), new Point3D(1.5, 1.0, 0),
          new Point3D(1.5, 1.5, 0)
        };
    double[] knots = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0};
    double[] w = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};

    NURBS n = new NURBS(points, knots, w, 3);
    Polyline p = new Polyline();

    NURBSFixedNTELSPointIterator it = new NURBSFixedNTELSPointIterator(n, 30);

    while (it.hasNext()) {
      p.addVertex(new Vertex(it.next()));
    }

    Point3D cornerPoint = n.getPointAt(1.0);
    int verticesOnCorner = 0;
    for (int i = 0; i < p.getVertexCount(); i++) {
      if (cornerPoint.equals(p.getVertex(i).getPoint())) {
        verticesOnCorner++;
      }
    }

    assertEquals("There needs to be exactly one Point on the corner", 1, verticesOnCorner);
  }
}
