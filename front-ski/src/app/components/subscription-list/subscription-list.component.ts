import { Component, OnInit } from '@angular/core';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { Subscription } from 'src/app/models/subscription.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-subscription-list',
  templateUrl: './subscription-list.component.html'
})
export class SubscriptionListComponent implements OnInit {

  subscriptions: Subscription[] = [];

  constructor(private subscriptionService: SubscriptionService, private router: Router) {}

  ngOnInit(): void {
    this.loadSubscriptions();
  }

  loadSubscriptions(): void {
    this.subscriptionService.getAll().subscribe(data => {
      this.subscriptions = data;
    });
  }

  deleteSubscription(id: number | undefined): void {
    if (id && confirm("Confirmer la suppression ?")) {
      this.subscriptionService.delete(id).subscribe(() => {
        this.loadSubscriptions();
      });
    }
  }

  editSubscription(id: number | undefined): void {
    this.router.navigate(['/edit', id]);
  }

  addNew(): void {
    this.router.navigate(['/add']);
  }
}
