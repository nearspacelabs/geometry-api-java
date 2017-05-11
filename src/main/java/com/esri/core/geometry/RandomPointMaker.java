package com.esri.core.geometry;

/**
 * Created by davidraleigh on 5/10/17.
 */

import java.util.Random;

public class RandomPointMaker {

    /**
     * This assumes an equal area projection
     * @param geometry
     * @param pointsPerSquareKm
     * @param sr
     * @param progressTracker
     * @return
     */
    static Geometry generate(Geometry geometry,
                             double pointsPerSquareKm,
                             Random numberGenerator,
                             SpatialReference sr,
                             ProgressTracker progressTracker) {
        if (geometry.getType() != Geometry.Type.Polygon)
            return null;

        Polygon polygon = (Polygon)geometry;
        return __makeRandomPoints(polygon, pointsPerSquareKm, numberGenerator, sr, progressTracker);
    }

    static Geometry __makeRandomPoints(Polygon polygon,
                                       double pointsPerSquareKm,
                                       Random numberGenerator,
                                       SpatialReference sr,
                                       ProgressTracker progressTracker) {

        // Calculate point count within bounding box by dividing area in meters
        // by 1000
        Envelope envelope = new Envelope();
        polygon.queryEnvelope(envelope);
        double areaKm = envelope.calculateArea2D() / 1000.0;

        double xmin = envelope.getXMin();
        double xmax = envelope.getXMax();
        double ymin = envelope.getYMin();
        double ymax = envelope.getYMax();

        int pointCount = (int) Math.ceil(areaKm * pointsPerSquareKm);

        double[] xy = new double[pointCount * 2];

        double val = 0.0;
        double xdiff = xmax - xmin;
        double ydiff = ymax - ymin;
        for (int i = 0; i < pointCount * 2; i++) {
            if (i % 2 == 0) // x val
                val = numberGenerator.nextDouble() * xdiff + xmin;
            else            // y val
                val = numberGenerator.nextDouble() * ydiff + ymin;

            xy[i] = val;
        }

        MultiPoint multiPoint = new MultiPoint();
        multiPoint.add(4, 4);
        MultiVertexGeometryImpl multiVertexGeometry = (MultiVertexGeometryImpl)multiPoint._getImpl();
        AttributeStreamOfDbl attributeStreamOfDbl = new AttributeStreamOfDbl(pointCount * 2);
        attributeStreamOfDbl.writeRange(0, pointCount * 2, xy, 0, true);
        multiVertexGeometry.setAttributeStreamRef(0, attributeStreamOfDbl);
        //multiVertexGeometry._resizeImpl(pointCount);
        multiPoint.resize(pointCount);

        return GeometryEngine.intersect(multiPoint, polygon, sr);
    }
}
