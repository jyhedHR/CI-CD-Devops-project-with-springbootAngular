package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tn.esprit.spring.repositories.IPisteRepository;

import static org.junit.jupiter.api.Assertions.*;

class IPisteServicesTest {
    @InjectMocks
    private PisteServicesImpl pisteServices;
    @Mock

    private IPisteRepository pisteRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    void retrieveAllPistes() {
    }

    @Test
    void addPiste() {
    }

    @Test
    void removePiste() {
    }

    @Test
    void retrievePiste() {
    }
}