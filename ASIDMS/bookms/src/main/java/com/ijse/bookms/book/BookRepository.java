package com.ijse.bookms.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b " +
           "WHERE (:query IS NULL OR " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(b.author.authorName) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Book> searchBooks(@Param("query") String query);

    // opção com @Query
@Query("SELECT b FROM Book b WHERE b.subcategory.category.categoryid = :categoryId")
List<Book> findBooksByCategory(@Param("categoryId") Long categoryId);

}
