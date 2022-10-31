package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("locations")
public class LocationReturn {
    @Id
    private String osm_id;
    private double lat;
    private double lon;
    private String display_name;
    private AddressDTO address;
}
