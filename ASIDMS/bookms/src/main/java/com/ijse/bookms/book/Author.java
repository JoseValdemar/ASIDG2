package com.ijse.bookms.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Author {
    public String getAuthorName() {
    return authorName;
}

public void setAuthorName(String authorName) {
    this.authorName = authorName;
}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorid")
    private Long id;

    @Column(unique = true , nullable = false)
    private String authorName;

    // @OneToMany(mappedBy = "authorid")
    // private Book book;
}
