package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.repositories.ISkierRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tn.esprit.spring.entities.TypeSubscription.MONTHLY;

@ExtendWith(MockitoExtension.class)
public class SkierServicesImplTest {

    @InjectMocks
    private SkierServicesImpl skierService;

    @Mock
    private ISkierRepository skierRepository;

    private Skier skier;

    @BeforeEach
    public void setUp() {
        skier = new Skier();
        skier.setNumSkier(1L);
    }

//    @Test
//    void addSkier() {
//        Subscription subscription = new Subscription();
//        subscription.setTypeSub(MONTHLY);
//        skier.setSubscription(subscription);
//
//        when(skierRepository.save(skier)).thenReturn(skier);
//        Skier savedSkier = skierService.addSkier(skier);
//
//        assertNotNull(savedSkier);
//        assertNotNull(savedSkier.getSubscription()); // Ensure subscription exists
//        assertEquals(MONTHLY, savedSkier.getSubscription().getTypeSub());
//
//        verify(skierRepository, times(1)).save(skier);
//    }

    @Test
    void retrieveAllSkiers() {
        List<Skier> skiers = Arrays.asList(new Skier(), new Skier());
        when(skierRepository.findAll()).thenReturn(skiers);

        List<Skier> result = skierService.retrieveAllSkiers();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(skierRepository, times(1)).findAll();
    }

    @Test
    void removeSkier() {
        doNothing().when(skierRepository).deleteById(1L);
        skierService.removeSkier(1L);
        verify(skierRepository, times(1)).deleteById(1L);
    }

    @Test
    void retrieveSkier() {
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        Skier foundSkier = skierService.retrieveSkier(1L);
        assertNotNull(foundSkier);
        assertEquals(1L, foundSkier.getNumSkier());
        verify(skierRepository, times(1)).findById(1L);
    }


}
