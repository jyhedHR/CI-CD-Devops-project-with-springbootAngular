import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubscriptionListComponent } from './components/subscription-list/subscription-list.component';
import { SubscriptionFormComponent } from './components/subscription-form/subscription-form.component';

const routes: Routes = [
  { path: '', component: SubscriptionListComponent },
  { path: 'add', component: SubscriptionFormComponent },
  { path: 'edit/:id', component: SubscriptionFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
