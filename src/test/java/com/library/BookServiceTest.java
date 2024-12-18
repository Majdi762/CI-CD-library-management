package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookDAO bookDAO; // Mock de la dépendance BookDAO

    @InjectMocks
    private BookService bookService; // Injecte le mock dans BookService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);// Initialise les mocks
    }

    @Test
    void testAddBook() {
        // Données de test
        Book book = new Book("Java Programming", "John Doe", "123456", 2020);

        // Appel de la méthode à tester
        bookService.addBook(book);

        // Vérification que la méthode DAO a bien été appelée
        verify(bookDAO, times(1)).add(book);
    }

    @Test
    void testUpdateBook() {
        // Données de test
        Book book = new Book("Java Programming", "John Doe", "123456", 2020);

        // Simulation : retourner un livre existant
        when(bookDAO.getBookById(1)).thenReturn(book);

        // Appel de la méthode à tester
        bookService.updateBook(1, "Advanced Java", "Jane Doe", true);

        // Vérification des interactions
        verify(bookDAO, times(1)).getBookById(1);
        verify(bookDAO, times(1)).update(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        // Appel de la méthode à tester
        bookService.deleteBook(1);

        // Vérification que la méthode DAO a bien été appelée
        verify(bookDAO, times(1)).delete(1);
    }
}
