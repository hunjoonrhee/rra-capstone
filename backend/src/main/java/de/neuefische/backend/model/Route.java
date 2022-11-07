package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("routes")
public class Route {
    @Id
    private String id;
    private String routeName;
    private String[] hashtags;
    private String imageThumbnail;
    private StartPosition startPosition;
    private EndPosition endPosition;
    private List<Routes> routes;
    private List<Photo> photos = new ArrayList<>();
    private GeoJsonPoint position;

    public Route(String id, String routeName, String[] hashtags,
                 String imageThumbnail, double x, double y) {
        this.id = id;
        this.routeName = routeName;
        this.hashtags = hashtags;
        this.imageThumbnail = imageThumbnail;
        this.position = new GeoJsonPoint(x,y);
    }
}
