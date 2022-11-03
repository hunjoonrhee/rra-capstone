package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class    RouteDTO {

    private String routeName;
    private String[] hashtags;
    private String imageThumbnail;
    private StartPosition startPosition;
    private EndPosition endPosition;

}
