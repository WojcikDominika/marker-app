package org.freddydurkee.marker.view.utils;

public class ImageViewPositionMapper {

    private ImageViewPositionMapper() {
    }

    /**
     * Maps original image marker (x or y) position to image view position.
     *
     * @param viewImageTopLeftCorner image view top left corner (x or y) position relative to the container
     * @param markerPosition         marker (x or y) position on original image
     * @param viewSize               image view size (x or y)
     * @param imageSize              original image size (x or y)
     * @return marker position on view image
     */
    public static double mapImagePositionToViewPosition(double viewImageTopLeftCorner, double markerPosition, double viewSize, double imageSize) {
        return viewImageTopLeftCorner + (markerPosition * viewSize / imageSize);
    }

    /**
     * Maps image view marker (x or y) position to original image position.
     *
     * @param imageViewMarkerPosition image view marker (x or y) position
     * @param viewSize                image view size (x or y)
     * @param imageSize               original image size (x or y)
     * @return
     */
    public static int mapViewPositionToImagePosition(double imageViewMarkerPosition, double viewSize, double imageSize) {
        return (int) (imageSize / viewSize * imageViewMarkerPosition);
    }
}
