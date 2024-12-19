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
        if (book != null) {
            bookDAO.add(book);
        } else {
            throw new IllegalArgumentException("Book cannot be null");
        }
    }

    public void displayBooks() {
        List<Book> books = bookDAO.getAllBooks();
        if (books != null && !books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("No books available");
        }
    }

    public Optional<Book> findBookById(int id) {
        return Optional.ofNullable(bookDAO.getBookById(id));
    }

    public void deleteBook(int id) {
        Optional<Book> book = findBookById(id);
        book.ifPresentOrElse(b -> bookDAO.delete(id),
                () -> System.out.println("Book not found"));
    }

    public void updateBook(int id, String title, String author, boolean available) {
        Optional<Book> bookOpt = findBookById(id);

        bookOpt.ifPresentOrElse(book -> {
            updateBookDetails(book, title, author, available);
            bookDAO.update(book);
        }, () -> System.out.println("Book not found"));
    }

    private void updateBookDetails(Book book, String title, String author, boolean available) {
        if (title != null && !title.isEmpty()) {
            book.setTitle(title);
        }
        if (author != null && !author.isEmpty()) {
            book.setAuthor(author);
        }
        book.setAvailable(available);
    }
}
