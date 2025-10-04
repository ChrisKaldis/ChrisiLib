package com.chrisilib.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.chrisilib.api.dto.BookDTO;
import com.chrisilib.api.dto.BookResponseDTO;
import com.chrisilib.api.model.Book;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    Book toBook(BookDTO bookDTO);

    void updateBookFromDto(BookDTO bookDTO, @MappingTarget Book book);

    // For converting a Book entity to a response DTO
    @Mapping(target = "bookcaseId", source = "bookcase.id")
    @Mapping(target = "bookcaseName", source = "bookcase.name")
    BookResponseDTO toBookResponseDTO(Book book);

}
