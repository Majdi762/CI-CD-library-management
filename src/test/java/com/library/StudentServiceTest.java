package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentDAO studentDAO;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStudent() {
        // Mock des données
        Student student = new Student(1, "Alice");

        // Appel de la méthode à tester
        studentService.addStudent(student);

        // Vérifications
        verify(studentDAO, times(1)).addStudent(student);
    }

    @Test
    void testUpdateStudent() {
        // Mock des données
        Student student = new Student(1, "Alice");

        // Appel de la méthode à tester
        studentDAO.updateStudent(student);

        // Vérifications
        verify(studentDAO, times(1)).updateStudent(student);
    }

    @Test
    void testDeleteStudent() {
        // Appel de la méthode à tester
        studentDAO.deleteStudent(1);

        // Vérifications
        verify(studentDAO, times(1)).deleteStudent(1);
    }

    @Test
    void testGetAllStudents() {
        // Mock des données
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Alice"));
        students.add(new Student(2, "Bob"));

        // Comportement des mocks
        when(studentDAO.getAllStudents()).thenReturn(students);

        // Appel de la méthode à tester
        List<Student> result = studentDAO.getAllStudents();

        // Vérifications
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        verify(studentDAO, times(1)).getAllStudents();
    }
}
