package org.freddydurkee.marker.view.utils;

import org.freddydurkee.marker.model.Point;

public class PointUtil {

    /**
     * Calculate new point position that lays inside rectangular region bounded by top left corner and bottom right corner.
     * If current point lays outside the rectangular region, the nearest point inside is returned.
     *
     * @param currentPoint           current position
     * @param topLeftCornerBound     top left corner bound
     * @param bottomRightCornerBound bottom right corner bound
     * @return new point position that lays inside rectangular region
     */
    public static Point getNearestPointWithin(Point currentPoint, Point topLeftCornerBound, Point bottomRightCornerBound) {
        double finalX = currentPoint.getX();
        double finalY = currentPoint.getY();

        if (finalX > bottomRightCornerBound.getX()) {
            finalX = bottomRightCornerBound.getX();
        } else if (finalX < topLeftCornerBound.getX()) {
            finalX = topLeftCornerBound.getX();
        }

        if (finalY > bottomRightCornerBound.getY()) {
            finalY = bottomRightCornerBound.getY();
        } else if (finalY < topLeftCornerBound.getY()) {
            finalY = topLeftCornerBound.getY();
        }
        return new Point(finalX, finalY);
    }
}
