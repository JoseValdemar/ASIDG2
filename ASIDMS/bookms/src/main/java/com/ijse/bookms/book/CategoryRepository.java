package com.ijse.bookms.book;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ijse.bookms.book.Category;



@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
    
}
