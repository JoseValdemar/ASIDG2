package com.ijse.bookms.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SearchController {
    @Autowired
    private BookService bookService;

     @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("query") String query) {
        List<Book> searchResults = bookService.searchBooks(query);

        if (searchResults != null && !searchResults.isEmpty()) {
            return new ResponseEntity<>(searchResults, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
