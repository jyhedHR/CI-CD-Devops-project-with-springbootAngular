package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISubscriptionRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


class SubscriptionServicesImplTest {
@InjectMocks
private SubscriptionServicesImpl subscriptionServices;
@Mock
private ISubscriptionRepository subscriptionRepository;
@BeforeEach
public void setUp() {
    MockitoAnnotations.openMocks(this);

}




    @Test
    void updateSubscription() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.MONTHLY);

        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        Subscription updatedSubscription = subscriptionServices.updateSubscription(subscription);

        assertNotNull(updatedSubscription);
        assertEquals(subscription.getNumSub(), updatedSubscription.getNumSub());

    }

    @Test
    void retrieveSubscriptionById() {
        Long subscriptionId = 1L;
        Subscription subscription = new Subscription();
        subscription.setNumSub(subscriptionId);

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(java.util.Optional.of(subscription));

        Subscription retrievedSubscription = subscriptionServices.retrieveSubscriptionById(subscriptionId);

        assertNotNull(retrievedSubscription);
        assertEquals(subscriptionId, retrievedSubscription.getNumSub());

    }



    @Test
    void retrieveSubscriptionsByDates() {
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now();
        List<Subscription> subscriptions = List.of(new Subscription());

        when(subscriptionRepository.getSubscriptionsByStartDateBetween(startDate, endDate)).thenReturn(subscriptions);

        List<Subscription> retrievedSubscriptions = subscriptionServices.retrieveSubscriptionsByDates(startDate, endDate);

        assertNotNull(retrievedSubscriptions);
        assertEquals(1, retrievedSubscriptions.size());
    }



}