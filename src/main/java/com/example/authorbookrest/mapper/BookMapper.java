package com.example.authorbookrest.mapper;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.CreateBookRequestDto;
import com.example.authorbookrest.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {

    @Mapping(target = "author.id", source = "authorId")
    Book map(CreateBookRequestDto dto);

    @Mapping(target = "authorDto", source = "author")
    BookDto mapToDto(Book entity);

    List<BookDto> mapListToDtos(List<Book> books);

}
