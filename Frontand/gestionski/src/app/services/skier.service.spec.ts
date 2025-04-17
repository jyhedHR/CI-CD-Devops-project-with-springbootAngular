import { TestBed } from '@angular/core/testing';

import { SkierService } from './skier.service';

describe('SkierService', () => {
  let service: SkierService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SkierService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
