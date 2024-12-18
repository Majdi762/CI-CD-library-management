package com.library;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Student;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.StudentService;
import com.library.util.DbConnection;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Création des objets DAO
        BookDAO bookDAO = new BookDAO();
        StudentDAO studentDAO = new StudentDAO(DbConnection.getConnection());
        BorrowDAO borrowDAO = new BorrowDAO();

        // Création des services
        BookService bookService = new BookService(bookDAO);
        StudentService studentService = new StudentService(studentDAO);
        BorrowService borrowService = new BorrowService(borrowDAO);

        boolean running = true;

        while (running) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Afficher les livres");
            System.out.println("3. Ajouter un étudiant");
            System.out.println("4. Afficher les étudiants");
            System.out.println("5. Emprunter un livre");
            System.out.println("6. Afficher les emprunts");
            System.out.println("7. Quitter");

            System.out.print("Choisir une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consommer la ligne restante après l'entier

            switch (choice) {
                case 1:
                    System.out.print("Entrez le titre du livre: ");
                    String title = scanner.nextLine();
                    System.out.print("Entrez l'auteur du livre: ");
                    String author = scanner.nextLine();
                    Book book = new Book(title, author, "", 0); // Modifier en fonction des données disponibles
                    bookService.addBook(book);
                    break;

                case 2:
                    bookService.displayBooks();
                    break;

                case 3:
                    System.out.print("Entrez le nom de l'étudiant: ");
                    String studentName = scanner.nextLine();
                    Student student = new Student(studentName);
                    studentService.addStudent(student);
                    break;

                case 4:
                    studentService.displayStudents();
                    break;

                case 5:
                    System.out.print("Entrez l'ID de l'étudiant: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Entrez l'ID du livre: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne restante

//                    Student studentForBorrow = studentService.findStudentById(studentId);
//                    Book bookForBorrow = bookService.findBookById(bookId).orElse(null);
//                    if (studentForBorrow != null && bookForBorrow != null) {
//                        Borrow borrow = new Borrow(studentForBorrow, bookForBorrow, new Date(), null);
//                        borrowService.borrowBook(borrow);
//                    } else {
//                        System.out.println("Étudiant ou livre introuvable.");
//                    }
                    System.out.print(borrowService.borrowBook(studentId, bookId));
                    break;

                case 6:
                    borrowService.displayBorrows();
                    break;

                case 7:
                    running = false;
                    System.out.println("Au revoir!");
                    break;

                default:
                    System.out.println("Option invalide.");
            }
        }

        scanner.close();
    }
}
