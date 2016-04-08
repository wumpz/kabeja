package org.kabeja.util;


import org.kabeja.entities.*;
import org.kabeja.math.Point3D;

public class LWPolylineConverter {

    public static LWPolyline toLWPolyline(Polyline pl) {
        LWPolyline lwp = new LWPolyline();
        lwp.setColor(pl.getColor());
        lwp.setLayer(pl.getLayer());
        lwp.setDocument(pl.getDocument());

        for (Vertex vertex : pl.getVertices()) {
            lwp.addVertex(new LW2DVertex(vertex.getPoint().getX(), vertex.getPoint().getY()));
        }

        pl.getLayer().removeEntity(pl);

        return lwp;
    }

    public static LWPolyline toLWPolyline(Line line) {
        LWPolyline lwp = new LWPolyline();
        lwp.setColor(line.getColor());
        lwp.setLayer(line.getLayer());
        lwp.setDocument(line.getDocument());

        Point3D startPoint = line.getStartPoint();
        Point3D endPoint = line.getEndPoint();
        lwp.addVertex(new LW2DVertex(startPoint.getX(), startPoint.getY()));
        lwp.addVertex(new LW2DVertex(endPoint.getX(), endPoint.getY()));

        line.getLayer().removeEntity(line);

        return lwp;
    }

}
