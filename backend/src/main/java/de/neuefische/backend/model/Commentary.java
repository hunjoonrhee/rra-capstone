package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("comments")
public class Commentary {

    @Id
    private String id;
    private String message;
    private String routeId;
    private AppUser postedBy;
    private String timeStamp;
}
