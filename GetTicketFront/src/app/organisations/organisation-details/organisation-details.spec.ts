import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganisationDetails } from './organisation-details';

describe('OrganisationDetails', () => {
  let component: OrganisationDetails;
  let fixture: ComponentFixture<OrganisationDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrganisationDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganisationDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
