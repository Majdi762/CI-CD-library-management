package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO();
        bookService = new BookService(bookDAO);
    }

    @Test
    void testAddBook() {
        Book book = new Book("Java Programming", "John Doe", "123456", 2020);
        bookService.addBook(book);
        assertEquals(1, bookDAO.getAllBooks().size());
        assertEquals("Java Programming", bookDAO.getBookById(1).getTitle());
    }

    @Test
    void testUpdateBook() {
        Book book = new Book("Java Programming", "John Doe", "123456", 2020);
        bookService.addBook(book);
        bookService.updateBook(1, "Advanced Java", "Jane Doe", true);
        assertEquals("Advanced Java", bookDAO.getBookById(1).getTitle());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book("Java Programming", "John Doe", "123456", 2020);
        bookService.addBook(book);
        bookService.deleteBook(1);
        assertNull(bookDAO.getBookById(1));
    }
}
