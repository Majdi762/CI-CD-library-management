//package com.library;
//
//import com.library.dao.BookDAO;
//import com.library.dao.BorrowDAO;
//import com.library.dao.StudentDAO;
//import com.library.model.Book;
//import com.library.model.Student;
//import com.library.model.Borrow;
//import com.library.service.BorrowService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class BorrowServiceTest {
//
//    @Mock
//    private BookDAO bookDAO;
//
//    @Mock
//    private StudentDAO studentDAO;
//
//    @Mock
//    private BorrowDAO borrowDAO;
//
//    @InjectMocks
//    private BorrowService borrowService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testBorrowBook() {
//        // Mock des données
//        Student student = new Student(1, "Alice");
//        Book book = new Book("Java Programming", "John Doe", "g1245", 2002);
//        book.setAvailable(true);
//
//        // Comportement des mocks
//        when(studentDAO.getStudentById(1)).thenReturn(student);
//        when(bookDAO.getBookById(1)).thenReturn(book);
//
//        // Appel de la méthode à tester
//        String result = borrowService.borrowBook(1, 1);
//
//        // Vérifications
//        assertEquals("Livre emprunté avec succès!", result);
//        verify(borrowDAO, times(1)).save(any(Borrow.class));
//        verify(bookDAO, times(1)).updateBookAvailability(1, false);
//    }
//
//    @Test
//    void testReturnBook() {
//        // Mock des données
//        Student student = new Student(1, "Alice");
//        Book book = new Book("Java Programming", "John Doe", "g1245", 2002);
//        book.setAvailable(false);
//
//        // Comportement des mocks
//        when(studentDAO.getStudentById(1)).thenReturn(student);
//        when(bookDAO.getBookById(1)).thenReturn(book);
//
//        // Appel de la méthode à tester
//        String result = borrowService.returnBook(1, 1);
//
//        // Vérifications
//        assertEquals("Livre retourné avec succès!", result);
//        verify(borrowDAO, times(1)).delete(1, 1);
//        verify(bookDAO, times(1)).updateBookAvailability(1, true);
//    }
//
//    @Test
//    void testBorrowBookNotAvailable() {
//        // Mock des données
//        Student student = new Student(1, "Alice");
//        Book book = new Book("Java Programming", "John Doe", "g1245", 2002);
//        book.setAvailable(false);
//
//        // Comportement des mocks
//        when(studentDAO.getStudentById(1)).thenReturn(student);
//        when(bookDAO.getBookById(1)).thenReturn(book);
//
//        // Appel de la méthode à tester
//        String result = borrowService.borrowBook(1, 1);
//
//        // Vérifications
//        assertEquals("Le livre n'est pas disponible.", result);
//        verify(borrowDAO, never()).save(any(Borrow.class));
//    }
//
//    @Test
//    void testBorrowBookStudentNotFound() {
//        // Comportement des mocks
//        when(studentDAO.getStudentById(3)).thenReturn(null);
//        when(bookDAO.getBookById(1)).thenReturn(null);
//
//        // Appel de la méthode à tester
//        String result = borrowService.borrowBook(3, 1);
//
//        // Vérifications
//        assertEquals("Étudiant ou livre non trouvé.", result);
//        verify(borrowDAO, never()).save(any(Borrow.class));
//    }
//}
