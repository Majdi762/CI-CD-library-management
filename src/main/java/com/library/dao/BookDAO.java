package com.library.dao;

import com.library.model.Book;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Ajouter un nouveau livre
    public void add(Book book) {
        String sql = "INSERT INTO books (title, author, isbn, published_year) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setInt(4, book.getPublishedYear());

            statement.executeUpdate();
            System.out.println("Livre inséré avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du livre : " + e.getMessage());
        }
    }

    // Récupérer un livre par son ID
    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublishedYear(resultSet.getInt("published_year"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du livre : " + e.getMessage());
        }

        return book;
    }

    // Récupérer tous les livres
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublishedYear(resultSet.getInt("published_year"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }

        return books;
    }

    // Supprimer un livre par ID
    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }

    // Mise à jour des informations d'un livre
    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, published_year = ? WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setInt(4, book.getPublishedYear());
            statement.setInt(5, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du livre : " + e.getMessage());
        }
    }
}
