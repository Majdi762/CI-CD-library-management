package com.library;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {

    @Mock
    private BookDAO bookDAO;

    @Mock
    private StudentDAO studentDAO;

    @Mock
    private BorrowDAO borrowDAO;

    @InjectMocks
    private BorrowService borrowService;  // Utilisez @InjectMocks pour injecter les mocks dans le service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testBorrowBookNotAvailable() {
        // Mock des données
        Student student = new Student("Alice");
        Book book = new Book("Java Programming", "John Doe", "g1245", 2002, false);
        System.out.println("Student1: " + studentDAO.getStudentById(0));
        System.out.println("Book1: " + bookDAO.getBookById(0));


        // Comportement des mocks
        when(studentDAO.getStudentById(0)).thenReturn(student);
        when(bookDAO.getBookById(0)).thenReturn(book);

        // Vérifications des mocks
        System.out.println("Student: " + studentDAO.getStudentById(0));
        System.out.println("Book: " + bookDAO.getBookById(0));

        // Appel de la méthode à tester
        String result = borrowService.borrowBook(0, 0);

        // Vérification du message retourné
        System.out.println("Result: " + result);
        assertEquals("Le livre n'est pas disponible.", result);

        // Vérifie que le livre n'a pas été emprunté
        verify(borrowDAO, never()).save(any(Borrow.class));
    }



    @Test
    void testBorrowBookStudentNotFound() {
//        // Comportement des mocks
//        when(studentDAO.getStudentById(3)).thenReturn(null);
//        when(bookDAO.getBookById(1)).thenReturn(null);

        // Appel de la méthode à tester
        String result = borrowService.borrowBook(3, 1);

        // Vérifications
        assertEquals("Étudiant ou livre non trouvé.", result);
        verify(borrowDAO, never()).save(any(Borrow.class));  // Vérifie que le livre n'a pas été emprunté
    }

    @Test
    void testBorrowBookSuccess() {
        // Mock des données
        Student student = new Student("Alice");
        student.setId(1);
        Book book = new Book("Java Programming", "John Doe", "g1245", 2002, true);
        book.setId(1);

        // Comportement des mocks
        when(studentDAO.getStudentById(1)).thenReturn(student);
        when(bookDAO.getBookById(1)).thenReturn(book);

        // Appel de la méthode à tester
        String result = borrowService.borrowBook(1, 1);

        // Vérifications
        assertEquals("Livre emprunté avec succès!", result);

        // Vérifie que le livre a été enregistré dans la table des emprunts
        verify(borrowDAO, times(1)).save(any(Borrow.class));

        // Vérifie que la disponibilité du livre a été mise à jour
        assertEquals(false, book.isAvailable());
    }


    @Test
    void testReturnBookSuccess() {
        // Mock des données
        Student student = new Student("Alice");
        student.setId(1);
        Book book = new Book("Java Programming", "John Doe", "g1245", 2002, false);
        book.setId(1);
        Borrow borrow = new Borrow(1, student, book, new Date(), null);

        // Comportement des mocks
        when(borrowDAO.findBorrowByStudentAndBook(1, 1)).thenReturn(borrow);

        // Appel de la méthode à tester
        String result = borrowService.returnBook(1, 1);

        // Vérifications
        assertEquals("Livre retourné avec succès!", result);

        // Vérifie que la date de retour a été définie
        assertEquals(new Date().getTime(), borrow.getReturnDate().getTime(), 1000); // Tolérance de 1 seconde

        // Vérifie que le livre est marqué comme disponible
        assertEquals(true, book.isAvailable());

        // Vérifie que les DAO ont été appelés correctement
        verify(borrowDAO, times(1)).save(borrow);
    }


}
