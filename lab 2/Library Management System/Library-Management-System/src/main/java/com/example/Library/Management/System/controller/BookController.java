package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.dto.BookDTO;
import com.example.Library.Management.System.service.BookService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/books")
//public class BookController {
//    @Autowired
//    private BookService bookService;
//
//    // Create Book
//    @PostMapping
//    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
//        BookDTO createdBook = bookService.createBook(bookDTO);
//        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
//    }
//
//    // Get Book by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
//        BookDTO book = bookService.getBookById(id);
//        return ResponseEntity.ok(book);
//    }
//
//    // Update Book
//    @PutMapping("/{id}")
//    public ResponseEntity<BookDTO> updateBook(
//            @PathVariable Long id,
//            @Valid @RequestBody BookDTO bookDTO
//    ) {
//        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
//        return ResponseEntity.ok(updatedBook);
//    }
//
//    // Delete Book
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
//        bookService.deleteBook(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // List All Books
//    @GetMapping
//    public ResponseEntity<List<BookDTO>> getAllBooks() {
//        List<BookDTO> books = bookService.getAllBooks();
//        return ResponseEntity.ok(books);
//    }
//}

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO bookDTO
    ) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
}