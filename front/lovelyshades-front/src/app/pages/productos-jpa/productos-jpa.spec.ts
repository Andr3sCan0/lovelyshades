import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductosJpa } from './productos-jpa';

describe('ProductosJpa', () => {
  let component: ProductosJpa;
  let fixture: ComponentFixture<ProductosJpa>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductosJpa],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductosJpa);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
