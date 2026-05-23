import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductoService } from './productos';

describe('Productos', () => {
  let component: ProductoService;
  let fixture: ComponentFixture<ProductoService>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductoService],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductoService);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
