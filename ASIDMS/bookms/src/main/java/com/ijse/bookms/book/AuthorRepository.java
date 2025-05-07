package com.ijse.bookms.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ijse.bookms.book.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    
}
