package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import com.library.util.DbConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private StudentService studentService;
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() throws SQLException {
        studentDAO = new StudentDAO(DbConnection.getConnection());
        studentService = new StudentService(studentDAO);
    }

    @Test
    void testAddStudent() {
        Student student = new Student(1, "Alice");
        studentService.addStudent(student);
        assertEquals(1, studentDAO.getAllStudents().size());
        assertEquals("Alice", studentDAO.getStudentById(1).getName());
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student(1, "Alice");
        studentService.addStudent(student);
        student.setName("Alice Smith");
        studentService.updateStudent(student);
        assertEquals("Alice Smith", studentDAO.getStudentById(1).getName());
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student(1, "Alice");
        studentService.addStudent(student);
        studentService.deleteStudent(1);
        assertNull(studentDAO.getStudentById(1));
    }

    @Test
    void testGetAllStudents() {
        studentService.addStudent(new Student(1, "Alice"));
        studentService.addStudent(new Student(2, "Bob"));
        assertEquals(2, studentDAO.getAllStudents().size());
    }
}
