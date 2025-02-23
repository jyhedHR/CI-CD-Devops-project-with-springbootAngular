package tn.esprit.spring.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.repositories.IRegistrationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServicesImplTest {
    @Mock
    IRegistrationRepository registrationRepository;
    @InjectMocks
    RegistrationServicesImpl registrationServices;

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

}