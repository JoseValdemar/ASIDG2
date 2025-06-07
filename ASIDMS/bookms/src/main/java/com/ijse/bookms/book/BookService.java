package com.ijse.bookms.book;

import java.util.List;

public interface BookService {
    List<Book> getAllBook();
    Book getBookById(Long id);
    List<Book> getBooksByCategoryID(Long id);
    List<Book> searchBooks(String query);
    Book patchBookQuantity(Long id, Book book);
    Book createBook(BookDTO bookDTO);
    public Book updateBook(Book book);

}
