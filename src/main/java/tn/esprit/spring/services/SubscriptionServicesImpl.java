package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class SubscriptionServicesImpl implements ISubscriptionServices{

    private ISubscriptionRepository subscriptionRepository;

    private ISkierRepository skierRepository;

    private final EmailService emailService;


    @Override
    public Subscription addSubscription(Subscription subscription) {

        Subscription saved= subscriptionRepository.save(subscription);

        String subject = "Confirmation de votre Abonnement";
        String body = "Bonjour,\n\nVotre Abonnement a bien été reçue.\nMerci pour votre confiance.";
        emailService.sendConfirmationEmail("jihedb01@gmail.com", subject, body);



        return saved;
    }

    @Override
    public Subscription updateSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription retrieveSubscriptionById(Long numSubscription) {
        return subscriptionRepository.findById(numSubscription).orElse(null);
    }

    @Override
    public Set<Subscription> getSubscriptionByType(TypeSubscription type) {
        return subscriptionRepository.findByTypeSubOrderByStartDateAsc(type);
    }

    @Override
    public List<Subscription> retrieveSubscriptionsByDates(LocalDate startDate, LocalDate endDate) {
        return subscriptionRepository.getSubscriptionsByStartDateBetween(startDate, endDate);
    }

    @Override
    @Scheduled(cron = "*/30 * * * * *") /* Cron expression to run a job every 30 secondes */
    public void retrieveSubscriptions() {
        for (Subscription sub: subscriptionRepository.findDistinctOrderByEndDateAsc()) {
            Skier   aSkier = skierRepository.findBySubscription(sub);
            log.info(sub.getNumSub().toString() + " | "+ sub.getEndDate().toString()
                    + " | "+ aSkier.getFirstName() + " " + aSkier.getLastName());
        }
    }


    @Override
    public List<Subscription> GetallSubscription() {
        return (List<Subscription>) subscriptionRepository.findAll();
    }



    @Override
    public Subscription assignSubscriptionDynamically(Long numSkier) {
        Skier skier = skierRepository.findById(numSkier)
                .orElseThrow(() -> new RuntimeException("Skier not found"));

        LocalDate today = LocalDate.now();
        int age = today.getYear() - skier.getDateOfBirth().getYear();

        LocalDate startDate = today;
        LocalDate endDate;
        Float price;
        TypeSubscription typeSub;

        boolean isWinter = (today.getMonthValue() == 12 || today.getMonthValue() <= 2);
        boolean isSummer = (today.getMonthValue() >= 6 && today.getMonthValue() <= 8);

        if (age < 18) {
            typeSub = TypeSubscription.MONTHLY;
            endDate = startDate.plusMonths(1);
            price = isWinter ? 90f : 60f;
        } else if (age <= 40) {
            typeSub = TypeSubscription.SEMESTRIEL;
            endDate = startDate.plusMonths(4);
            price = isWinter ? 200f : 140f;
        } else {
            typeSub = TypeSubscription.ANNUAL;
            endDate = startDate.plusYears(1);
            price = isSummer ? 160f : 220f;
        }

        Subscription subscription = new Subscription();
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setTypeSub(typeSub);
        subscription.setPrice(price);

        Subscription savedSub = subscriptionRepository.save(subscription);
        skier.setSubscription(savedSub);
        skierRepository.save(skier);

        return savedSub;
    }


    @Override
    public void removeSubscription(Long numSub) {
        subscriptionRepository.deleteById(numSub);
    }

    @Override
    public Subscription getsubById(Long numSub) {
        return subscriptionRepository.findById(numSub)
                .orElseThrow(() -> new RuntimeException("subs non trouvée"));
    }

}
