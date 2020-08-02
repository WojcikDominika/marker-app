package org.freddydurkee.marker.view.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.freddydurkee.marker.view.utils.ImageViewPositionMapper.mapImagePositionToViewPosition;
import static org.junit.jupiter.api.Assertions.*;

class ImageViewPositionMapperTest {

    @ParameterizedTest(name = "{index} {5}")
    @CsvSource(value = {
        "0:8:20:100:1.6:Marker within image",
        "0:100:20:100:20:Marker at boundary",
        "84:550:560:800:469:ImageView is shifted in container"}, delimiter = ':')
    void mapsImageMarkerPositionToViewPosition(double viewImageTopLeftCorner,
                                         double markerPosition,
                                         double viewSize,
                                         double imageSize,
                                         double expected,
                                         String caseName) {
        double actualValue = mapImagePositionToViewPosition(viewImageTopLeftCorner, markerPosition, viewSize, imageSize);
        assertEquals(expected, actualValue);
    }

}