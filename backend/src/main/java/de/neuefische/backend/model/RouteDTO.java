package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class    RouteDTO {

    private String routeName;
    private String[] hashtags;
    private String imageThumbnail;
    private StartPosition startPosition;
    private List<Position> betweenPositions = new ArrayList<>();
    private EndPosition endPosition;
    private String createdBy;

}
