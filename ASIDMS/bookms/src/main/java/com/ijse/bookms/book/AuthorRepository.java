package com.ijse.bookms.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByAuthorName(String authorName);
}
