package com.mgmetehan.bookservice.repository;

import com.mgmetehan.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String>{
    Optional<Book> getBookByIsbn(String isbn);
}
