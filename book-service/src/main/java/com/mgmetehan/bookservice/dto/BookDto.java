package com.mgmetehan.bookservice.dto;

import com.mgmetehan.bookservice.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private BookIdDto id;
    private String title;
    private int bookYear;
    private String author;
    private String pressName;

    public static BookDto convert(Book from) {
        return new BookDto(
                from.getId() != null ? BookIdDto.convert(from.getId(), from.getIsbn()) : null,
                from.getTitle(),
                from.getBookYear(),
                from.getAuthor(),
                from.getPressName()
        );
    }
}
