package com.chrisilib.api.dto;

import lombok.Data;

@Data
public class BookDTO {

    private String Title;
    private String Author;
    private String isbn;
    private Long locationId;
    private Long ownerId;

}
