package com.ijse.bookms.book.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.ijse.bookms.book.Book;
import com.ijse.bookms.book.BookDTO;
import com.ijse.bookms.book.BookRepository;
import com.ijse.bookms.book.BookService;
import com.ijse.bookms.book.Author;
import com.ijse.bookms.book.AuthorRepository;
import com.ijse.bookms.dto.ItemDTO;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getBooksByCategoryID(Long id) {
       return bookRepository.findBooksByCategory(id);
    }

    @Override
    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

    @Override
    public Book patchBookQuantity(Long id, Book book) {
        Book existBook = bookRepository.findById(id).orElse(null);
        if (existBook != null) {
            existBook.setStock(book.getStock()); // stock atualizado
            return bookRepository.save(existBook);
        } else {
            return null;
        }
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        // Verifica se o autor já existe
        Author author = authorRepository.findByAuthorName(bookDTO.getAuthor());
        if (author == null) {
            author = new Author();
            author.setAuthorName(bookDTO.getAuthor());
            author = authorRepository.save(author);
        }

        // Criação do livro
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setPrice(bookDTO.getPrice());
        book.setStock(bookDTO.getStock());
        book.setAuthor(author);

        return bookRepository.save(book);
    }
   
public void decreaseStock(List<ItemDTO> items) {
    for (ItemDTO item : items) {
    Optional<Book> opt = bookRepository.findById(item.getBookId());
    if (opt.isPresent()) {
        Book book = opt.get();
        if (book.getStock() >= item.getQuantity()) {
            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);
        } else {
            throw new IllegalStateException("Stock insuficiente para o livro ID: " + item.getBookId());
        }
    } else {
        throw new IllegalStateException("Livro não encontrado ID: " + item.getBookId());
    }
    }
}
// na classe BookServiceImpl
@Override
public Book updateBook(Book book) {
    return bookRepository.save(book);
}

}
