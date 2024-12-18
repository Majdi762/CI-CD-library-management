package com.library.dao;

import com.library.model.Borrow;
import com.library.model.Book;
import com.library.model.Student;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT * FROM borrows";
        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // On suppose que vous avez une méthode pour récupérer un Book et un Student par leur ID
                Book book = getBookById(rs.getInt("book_id"));
                Student student = getStudentById(rs.getInt("student_id"));

                Borrow borrow = new Borrow(
                        rs.getInt("id"),
                        student,
                        book,
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date")
                );
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

    private Book getBookById(int bookId) {
        // Logic to fetch book by ID from the database
        return null;
    }

    private Student getStudentById(int studentId) {
        // Logic to fetch student by ID from the database
        return null;
    }

    public void save(Borrow borrow) {
        String query = "INSERT INTO borrows (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrow.getStudent().getId());
            stmt.setInt(2, borrow.getBook().getId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
