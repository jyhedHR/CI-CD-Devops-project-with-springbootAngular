package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tn.esprit.spring.repositories.ISubscriptionRepository;

class SubscriptionServicesImplTest {
@InjectMocks
private SubscriptionServicesImpl subscriptionServices;
@Mock
private ISubscriptionRepository subscriptionRepository;
@BeforeEach
public void setUp() {

}
    @Test

    void addSubscription() {
    }

    @Test
    void updateSubscription() {
    }

    @Test
    void retrieveSubscriptionById() {
    }

    @Test
    void getSubscriptionByType() {
    }

    @Test
    void retrieveSubscriptionsByDates() {
    }

    @Test
    void retrieveSubscriptions() {
    }
}