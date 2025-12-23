package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.dbo;

//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDBO {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String address;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer carParks;
    private String furnishing;
    private Double sizeSqft;
    private String location;

    //@LOB stands for for “Large Object”. it tells the database to store the field as a large text or binary column (depending on the type).
//    @Lob
    private String imageUrl;

//    @Lob
    private String amenities;

//    @Lob
    private String commonFacilities;
}
