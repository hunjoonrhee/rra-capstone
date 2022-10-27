package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String house_number;
    private String road;
    private String city;
    private String state;
    private String postcode;
    private String country;
    private String country_code;
}
