import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeOrganisation } from './liste-organisation';

describe('ListeOrganisation', () => {
  let component: ListeOrganisation;
  let fixture: ComponentFixture<ListeOrganisation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListeOrganisation]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListeOrganisation);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
