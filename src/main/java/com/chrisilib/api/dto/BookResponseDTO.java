package com.chrisilib.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object for representing a Book in API responses.
 * This class provides a client-friendly, flattened structure of a Book entity,
 * decoupling the API response from the internal database model.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private Long ownerId;

    private Long bookcaseId;
    private String bookcaseName;

}
