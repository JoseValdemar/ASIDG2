package com.ijse.bookms.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {
        Book createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book existBook = bookService.getBookById(id);
        if (existBook != null) {
            return new ResponseEntity<>(existBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBook();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/category/{id}")
    public ResponseEntity<List<Book>> getBooksByCategoryID(@PathVariable Long id) {
        List<Book> existBook = bookService.getBooksByCategoryID(id);
        if (existBook != null) {
            return new ResponseEntity<>(existBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/updatequantity/{id}")
    public ResponseEntity<Book> patchQuantity(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBookQuantity = bookService.patchBookQuantity(id, book);
        return new ResponseEntity<>(updatedBookQuantity, HttpStatus.OK);
    }
}
