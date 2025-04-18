import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SubscriptionListComponent } from './components/subscription-list/subscription-list.component';
import { SubscriptionFormComponent } from './components/subscription-form/subscription-form.component';

@NgModule({
  declarations: [
    AppComponent,
    SubscriptionListComponent,
    SubscriptionFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
