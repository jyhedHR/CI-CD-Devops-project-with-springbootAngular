package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.services.ISubscriptionServices;
import tn.esprit.spring.utils.PdfGenerator;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Tag(name = "\uD83D\uDC65 Subscription Management")
@RestController
@CrossOrigin(origins = "http://192.168.33.10:4200")
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionRestController {

    private final ISubscriptionServices subscriptionServices;

    @Operation(description = "Add Subscription ")
    @PostMapping("/add")
    public Subscription addSubscription(@RequestBody Subscription subscription){
        return  subscriptionServices.addSubscription(subscription);
    }
    @Operation(description = "Retrieve Subscription by Id")
    @GetMapping("/get/{id-subscription}")
    public Subscription getById(@PathVariable("id-subscription") Long numSubscription){
        return subscriptionServices.retrieveSubscriptionById(numSubscription);
    }
    
    @Operation(description = "Retrieve Subscriptions by Type")
    @GetMapping("/all/{typeSub}")
    public Set<Subscription> getSubscriptionsByType(@PathVariable("typeSub")TypeSubscription typeSubscription){
        return subscriptionServices.getSubscriptionByType(typeSubscription);
    }
    @Operation(description = "Update Subscription ")
    @PutMapping("/update")
    public Subscription updateSubscription(@RequestBody Subscription subscription){
        return  subscriptionServices.updateSubscription(subscription);
    }
    @Operation(description = "Retrieve Subscriptions created between two dates")
    @GetMapping("/all/{date1}/{date2}")
    public List<Subscription> getSubscriptionsByDates(@PathVariable("date1") LocalDate startDate,
                                                      @PathVariable("date2") LocalDate endDate){
        return subscriptionServices.retrieveSubscriptionsByDates(startDate, endDate);
    }

    @GetMapping("/getall")
    public List<Subscription> getSubscriptionsByDates(){
        return (List<Subscription>) subscriptionServices.GetallSubscription();
    }


    @Operation(description = "Affecter dynamiquement un abonnement à un skieur")
    @PostMapping("/assign-dynamic/{skierId}")
    public Subscription assignDynamic(@PathVariable Long skierId) {
        return subscriptionServices.assignSubscriptionDynamically(skierId);
    }


    @GetMapping("/sub/{numSub}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long numSub) {
        Subscription reclamation = subscriptionServices.getsubById(numSub);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfGenerator.generateReclamationPdfStream(reclamation, out);

            byte[] pdfBytes = out.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Subscription" + numSub + ".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/delete/{numSub}")
    public void deleteById(@PathVariable("numSub") Long numSub){
        subscriptionServices.removeSubscription(numSub);
    }



}
