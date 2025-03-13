package com.example.Library.Management.System.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

//public class BookDTO {
//    private Long id;
//
//    @NotBlank(message = "Title is required")
//    private String title;
//
//    @NotBlank(message = "Author is required")
//    private String author;
//
//    @NotNull(message = "Publication year is required")
//    private Integer publicationYear;
//
//    @NotBlank(message = "ISBN is required")
//    private String isbn;
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public Integer getPublicationYear() {
//        return publicationYear;
//    }
//
//    public void setPublicationYear(Integer publicationYear) {
//        this.publicationYear = publicationYear;
//    }
//
//    public String getIsbn() {
//        return isbn;
//    }
//
//    public void setIsbn(String isbn) {
//        this.isbn = isbn;
//    }
//}
@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100)
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotNull(message = "Publication year is required")
    private Integer publicationYear;

    @Pattern(regexp = "^\\d{10}$", message = "ISBN must be 10 digits")
    private String isbn;
}
