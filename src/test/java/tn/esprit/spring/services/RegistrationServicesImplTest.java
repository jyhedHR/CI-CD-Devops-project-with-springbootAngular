package tn.esprit.spring.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServicesImplTest {
    @Mock
    IRegistrationRepository registrationRepository;
    @Mock
    ISkierRepository skierRepository;

    @Mock
    ICourseRepository courseRepository;
    @InjectMocks
    RegistrationServicesImpl registrationServices;
//CRUD test
    @Test
    public void addRegistrationTest() {
        Registration registration = new Registration();
        registration.setNumRegistration(1L);
        registration.setNumWeek(1);
        when(registrationRepository.save(registration)).thenReturn(registration);
        Registration addedRegistration = registrationServices.addRegistration(registration);
        //test registration == matched registration
        assertNotNull(addedRegistration);
        assertEquals(registration.getNumRegistration(),addedRegistration.getNumRegistration());
        assertEquals(registration.getNumWeek(),addedRegistration.getNumWeek());
        assertTrue(registration.getNumRegistration()==1);
    }
    @Test
    public void updateRegistrationTest() {
        Registration registration = new Registration();
        registration.setNumRegistration(1L);
        registration.setNumWeek(1);
        when(registrationRepository.save(registration)).thenReturn(registration);
        Registration updatedRegistration = registrationServices.updateRegistration(registration);
        //test registration == matched registration
        assertNotNull(updatedRegistration);
        assertEquals(registration.getNumRegistration(),updatedRegistration.getNumRegistration());
        assertEquals(registration.getNumWeek(),updatedRegistration.getNumWeek());
        assertTrue(registration.getNumRegistration()==1);
    }
    @Test
    public void retrieveRegistrationTest() {
        Registration registration = new Registration();
        registration.setNumRegistration(1L);
        registration.setNumWeek(1);
        when(registrationRepository.findById(1L)).thenReturn(java.util.Optional.of(registration));
        Registration retrievedRegistration = registrationServices.retrieveRegistration(1L);
        //test registration == matched registration
        assertNotNull(retrievedRegistration);
        assertEquals(registration.getNumRegistration(),retrievedRegistration.getNumRegistration());
        assertEquals(registration.getNumWeek(),retrievedRegistration.getNumWeek());
        assertTrue(registration.getNumRegistration()==1);
    }
    @Test
    public void DeleteRegistrationTest() {
        doNothing().when(registrationRepository).deleteById(1L);
        Registration registration = new Registration();
        registration.setNumRegistration(1L);
        registration.setNumWeek(1);
        registrationServices.deleteRegistration(1L);
        verify(registrationRepository, times(1)).deleteById(1L);
    }
//Fonctionnalité avancé
@Test
void AddRegistrationAndAssignToSkierTest() {
    Registration registration = new Registration();
    Skier skier = new Skier();
    skier.setNumSkier(1L);

    when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
    when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

    Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);
    assertNotNull(result);
    verify(registrationRepository, times(1)).save(registration);
    assertEquals(skier, registration.getSkier());
}

    @Test
    void AssignRegistrationToCourseTest() {
        Registration registration = new Registration();
        Course course = new Course();
        course.setNumCourse(1L);
        registration.setNumRegistration(1L);

        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration result = registrationServices.assignRegistrationToCourse(1L, 1L);
        assertNotNull(result);
        verify(registrationRepository, times(1)).save(registration);
        assertEquals(course, registration.getCourse());
    }

    @Test
    void AddRegistrationAndAssignToSkierAndCourse_individualTest() {
        Registration registration = new Registration();
        registration.setNumWeek(5);
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setDateOfBirth(LocalDate.of(2000, 1, 1));

        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.INDIVIDUAL);

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), anyLong(), anyLong())).thenReturn(0L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);
        assertNotNull(result);
        assertEquals(course, registration.getCourse());
        assertEquals(skier, registration.getSkier());
    }

    @Test
    void AddRegistrationAndAssignToSkierAndCourse_child_full_courseTest() {
        Registration registration = new Registration();
        registration.setNumWeek(2);
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setDateOfBirth(LocalDate.now().minusYears(10));

        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), anyLong(), anyLong())).thenReturn(0L);
        when(registrationRepository.countByCourseAndNumWeek(course, registration.getNumWeek())).thenReturn(6L); // full

        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);
        assertNull(result); // because course is full
    }

    @Test
    void AddRegistrationAndAssignToSkierAndCourse_adult_allowedTest() {
        Registration registration = new Registration();
        registration.setNumWeek(3);
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setDateOfBirth(LocalDate.now().minusYears(25));

        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), anyLong(), anyLong())).thenReturn(0L);
        when(registrationRepository.countByCourseAndNumWeek(course, registration.getNumWeek())).thenReturn(2L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);
        assertNotNull(result);
        assertEquals(course, registration.getCourse());
        assertEquals(skier, registration.getSkier());
    }

    @Test
    void AddRegistrationAndAssignToSkierAndCourse_courseAlreadyRegisteredTest() {
        Registration registration = new Registration();
        registration.setNumWeek(1);
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.INDIVIDUAL);

        when(skierRepository.findById(anyLong())).thenReturn(Optional.of(skier));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), anyLong(), anyLong())).thenReturn(1L);

        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);
        assertNull(result);
    }

    @Test
    void NumWeeksCourseOfInstructorBySupportTest() {
        Long numInstructor = 1L;
        Support support = Support.SKI;
        List<Integer> expectedWeeks = Arrays.asList(1, 2, 3);

        when(registrationRepository.numWeeksCourseOfInstructorBySupport(numInstructor, support)).thenReturn(expectedWeeks);

        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(numInstructor, support);
        assertEquals(expectedWeeks, result);
        verify(registrationRepository, times(1)).numWeeksCourseOfInstructorBySupport(numInstructor, support);
    }
}