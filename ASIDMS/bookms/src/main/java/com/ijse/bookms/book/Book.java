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

    public int getStock() {
    return stock;
}

public void setStock(int stock) {
    this.stock = stock;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public double getPrice() {
    return price;
}

public void setPrice(double price) {
    this.price = price;
}

public Author getAuthor() {
    return author;
}

public void setAuthor(Author author) {
    this.author = author;
}
}
