package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PisteServicesImplTest {

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @Mock
    private IPisteRepository pisteRepository;

    private Piste piste;

    @BeforeEach
    void setUp() {
        piste = new Piste();
        piste.setNumPiste(1L);

    }

    @Test
    void retrieveAllPistes() {
        List<Piste> pistes = Arrays.asList(piste, new Piste());
        when(pisteRepository.findAll()).thenReturn(pistes);

        List<Piste> result = pisteServices.retrieveAllPistes();

        assertEquals(2, result.size());
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void addPiste() {
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        Piste savedPiste = pisteServices.addPiste(piste);

        assertNotNull(savedPiste);
        assertEquals(piste.getNumPiste(), savedPiste.getNumPiste());
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    void removePiste() {
        doNothing().when(pisteRepository).deleteById(piste.getNumPiste());

        pisteServices.removePiste(piste.getNumPiste());

        verify(pisteRepository, times(1)).deleteById(piste.getNumPiste());
    }

    @Test
    void retrievePiste() {
        when(pisteRepository.findById(piste.getNumPiste())).thenReturn(Optional.of(piste));

        Piste foundPiste = pisteServices.retrievePiste(piste.getNumPiste());

        assertNotNull(foundPiste);
        assertEquals(piste.getNumPiste(), foundPiste.getNumPiste());
        verify(pisteRepository, times(1)).findById(piste.getNumPiste());
    }
}
