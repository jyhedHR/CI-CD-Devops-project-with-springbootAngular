package tn.esprit.spring.services;

import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class InstructorServicesImplTest {

    @InjectMocks
    private InstructorServicesImpl instructorService;

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    private Instructor instructor;
    private Course course;

    @BeforeEach
    public void setUp() {
        // Initialize the Instructor entity with realistic data
        instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");
        instructor.setDateOfHire(LocalDate.of(2020, 1, 1));
        instructor.setCourses(new HashSet<>());  // Empty courses for now

        // Initialize the Course entity with realistic data
        course = new Course();
        course.setNumCourse(1L);
        course.setLevel(1);
        course.setPrice(100.0f);
        course.setTimeSlot(3);
    }

    @Test
    public void testAddInstructor() {
        // Arrange
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor result = instructorService.addInstructor(instructor);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(LocalDate.of(2020, 1, 1), result.getDateOfHire());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    public void testRetrieveAllInstructors() {
        // Arrange
        when(instructorRepository.findAll()).thenReturn(java.util.Collections.singletonList(instructor));

        // Act
        var instructors = instructorService.retrieveAllInstructors();

        // Assert
        assertFalse(instructors.isEmpty());
        assertEquals(1, instructors.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateInstructor() {
        // Arrange
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor result = instructorService.updateInstructor(instructor);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(LocalDate.of(2020, 1, 1), result.getDateOfHire());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    public void testRetrieveInstructor() {
        // Arrange
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        // Act
        Instructor result = instructorService.retrieveInstructor(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(LocalDate.of(2020, 1, 1), result.getDateOfHire());
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        // Arrange
        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        instructor.setCourses(courseSet);

        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Act
        Instructor result = instructorService.addInstructorAndAssignToCourse(instructor, 1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.getCourses().contains(course));
        verify(instructorRepository, times(1)).save(instructor);
        verify(courseRepository, times(1)).findById(1L);
    }
}
