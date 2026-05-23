import { TestBed } from '@angular/core/testing';

import { ProductoJpaService } from './producto-jpa.service';

describe('ProductoJpaService', () => {
  let service: ProductoJpaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductoJpaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
