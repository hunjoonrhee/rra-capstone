package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDTO {

    private String q;
    private String format;
    private String addressdetails;
    private String polygon_geojson;

    @Override
    public String toString() {
        return
                "?q=" + q +
                "&format=" + format+
                "&addressdetails=" + addressdetails+
                "&polygon_geojson=" + polygon_geojson;
    }
}
