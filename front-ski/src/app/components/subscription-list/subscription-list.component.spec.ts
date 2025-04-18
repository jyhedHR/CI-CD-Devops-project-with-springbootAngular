import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionListComponent } from './subscription-list.component';

describe('SubscriptionListComponent', () => {
  let component: SubscriptionListComponent;
  let fixture: ComponentFixture<SubscriptionListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubscriptionListComponent]
    });
    fixture = TestBed.createComponent(SubscriptionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
