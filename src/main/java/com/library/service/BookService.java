package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public void addBook(Book book) {
        bookDAO.add(book);
    }

    public void displayBooks() {
        List<Book> books = bookDAO.getAllBooks();
        books.forEach(System.out::println);
    }

    public Optional<Book> findBookById(int id) {
        return Optional.ofNullable(bookDAO.getBookById(id));
    }

    public void deleteBook(int id) {
        bookDAO.delete(id);
    }

    public void updateBook(int id, String title, String author, boolean available) {
        Book book = bookDAO.getBookById(id);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setAvailable(available);
            bookDAO.update(book);
        }
    }
}
