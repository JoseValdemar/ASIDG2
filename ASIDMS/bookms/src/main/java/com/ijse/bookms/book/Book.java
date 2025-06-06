package com.ijse.bookms.book;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookid")
    private Long id;

    @Column
    private String title;

    @Column
    private double price;

    @Column
    private int stock;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

}
