package com.chrisilib.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDTO {

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @NotBlank(message = "Author cannot be blank")
    @Size(max = 255)
    private String author;

    private String isbn;

    @NotNull(message = "Bookcase ID cannot be null")
    private Long bookcaseId;

    @NotNull(message = "Owner ID cannot be null")
    private Long ownerId;

}
