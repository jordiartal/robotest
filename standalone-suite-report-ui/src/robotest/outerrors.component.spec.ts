import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OutErrorsComponent } from './outerrors.component';

describe('JsonTreeComponent', () => {
  let component: OutErrorsComponent;
  let fixture: ComponentFixture<OutErrorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutErrorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutErrorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
