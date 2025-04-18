import { Component, OnInit } from '@angular/core';
import { Subscription, TypeSubscription } from 'src/app/models/subscription.model';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-subscription-form',
  templateUrl: './subscription-form.component.html'
})
export class SubscriptionFormComponent implements OnInit {

  subscription: Subscription = {
    startDate: '',
    endDate: '',
    price: 0,
    typeSub: TypeSubscription.MONTHLY
  };

  isEdit = false;
  typeSubValues = Object.values(TypeSubscription);

  constructor(
    private subscriptionService: SubscriptionService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.subscriptionService.getById(+id).subscribe(data => {
        this.subscription = data;
      });
    }
  }

  onSubmit(): void {
    if (this.isEdit) {
      this.subscriptionService.update(this.subscription).subscribe(() => {
        this.router.navigate(['/']);
      });
    } else {
      this.subscriptionService.create(this.subscription).subscribe(() => {
        this.router.navigate(['/']);
      });
    }
  }
}
