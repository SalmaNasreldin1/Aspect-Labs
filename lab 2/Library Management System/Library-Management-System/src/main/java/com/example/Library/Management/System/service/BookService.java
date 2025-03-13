package com.example.Library.Management.System.service;

import com.example.Library.Management.System.entity.Book;
import com.example.Library.Management.System.dto.BookDTO;
import com.example.Library.Management.System.repository.BookRepository;
import com.example.Library.Management.System.exception.ResourceNotFoundException;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class BookService {
//    @Autowired
//    private BookRepository bookRepository;
//
//    // Create Book
//    public BookDTO createBook(BookDTO bookDTO) {
//        Book book = new Book();
//        BeanUtils.copyProperties(bookDTO, book);
//        Book savedBook = bookRepository.save(book);
//
//        BookDTO responseDTO = new BookDTO();
//        BeanUtils.copyProperties(savedBook, responseDTO);
//        return responseDTO;
//    }
//
//    // Get Book by ID
//    public BookDTO getBookById(Long id) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
//
//        BookDTO bookDTO = new BookDTO();
//        BeanUtils.copyProperties(book, bookDTO);
//        return bookDTO;
//    }
//
//    // Update Book
//    public BookDTO updateBook(Long id, BookDTO bookDTO) {
//        Book existingBook = bookRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
//
//        // Update only non-null fields
//        if (bookDTO.getTitle() != null) existingBook.setTitle(bookDTO.getTitle());
//        if (bookDTO.getAuthor() != null) existingBook.setAuthor(bookDTO.getAuthor());
//        if (bookDTO.getPublicationYear() != null) existingBook.setPublicationYear(bookDTO.getPublicationYear());
//        if (bookDTO.getIsbn() != null) existingBook.setIsbn(bookDTO.getIsbn());
//
//        Book updatedBook = bookRepository.save(existingBook);
//
//        BookDTO responseDTO = new BookDTO();
//        BeanUtils.copyProperties(updatedBook, responseDTO);
//        return responseDTO;
//    }
//
//    // Delete Book
//    public void deleteBook(Long id) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
//
//        bookRepository.delete(book);
//    }
//
//    // List All Books
//    public List<BookDTO> getAllBooks() {
//        return bookRepository.findAll().stream()
//                .map(book -> {
//                    BookDTO dto = new BookDTO();
//                    BeanUtils.copyProperties(book, dto);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//}

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO, book);
        Book savedBook = bookRepository.save(book);

        BookDTO responseDTO = new BookDTO();
        BeanUtils.copyProperties(savedBook, responseDTO);
        return responseDTO;
    }

    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book, bookDTO);
        return bookDTO;
    }

    @Transactional
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Selective update
        if (bookDTO.getTitle() != null) existingBook.setTitle(bookDTO.getTitle());
        if (bookDTO.getAuthor() != null) existingBook.setAuthor(bookDTO.getAuthor());
        if (bookDTO.getPublicationYear() != null) existingBook.setPublicationYear(bookDTO.getPublicationYear());
        if (bookDTO.getIsbn() != null) existingBook.setIsbn(bookDTO.getIsbn());

        Book updatedBook = bookRepository.save(existingBook);

        BookDTO responseDTO = new BookDTO();
        BeanUtils.copyProperties(updatedBook, responseDTO);
        return responseDTO;
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> {
                    BookDTO dto = new BookDTO();
                    BeanUtils.copyProperties(book, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}