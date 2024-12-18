package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.dao.BorrowDAO;
import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;

import java.util.Date;
import java.util.List;

public class BorrowService {

    private BorrowDAO borrowDAO;
    private BookDAO bookDAO;
    private StudentDAO studentDAO;

    public BorrowService(BorrowDAO borrowDAO) {
        this.borrowDAO = borrowDAO;
    }

    public String borrowBook(int studentId, int bookId) {
        Student student = studentDAO.getStudentById(studentId);
        Book book = bookDAO.getBookById(bookId);

        if (student == null || book == null) {
            return "Étudiant ou livre non trouvé.";
        }

        if (!book.isAvailable()) {
            return "Le livre n'est pas disponible.";
        }

        Borrow borrow = new Borrow(0, student, book, new Date(), null);
        borrowDAO.save(borrow);
        book.setAvailable(false);  // Marquer le livre comme non disponible après l'emprunt
        return "Livre emprunté avec succès!";
    }


    public String returnBook(int studentId, int bookId) {
        Borrow borrow = findBorrowByStudentAndBook(studentId, bookId);

        if (borrow == null) {
            return "Emprunt non trouvé.";
        }

        borrow.setReturnDate(new Date());
        borrowDAO.save(borrow);

        Book book = borrow.getBook();
        book.setAvailable(true);  // Mark the book as available after returning
        return "Livre retourné avec succès!";
    }

    public List<Borrow> displayBorrows() {
        return borrowDAO.getAllBorrows();
    }

    public Student findStudentById(int studentId) {
        // Logic to find a student by ID
        return borrowDAO.getStudentById(studentId);
    }

    private Book findBookById(int bookId) {
        // Logic to find a book by ID
        return bookDAO.getBookById(bookId);
    }

    private Borrow findBorrowByStudentAndBook(int studentId, int bookId) {
        // Logic to find borrow by student and book
        return borrowDAO.findBorrowByStudentAndBook(studentId, bookId);
    }
}
