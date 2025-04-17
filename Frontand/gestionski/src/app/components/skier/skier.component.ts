import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SkierService } from 'src/app/services/skier.service';
import { Skier } from 'src/app/models/skier.model';

@Component({
  selector: 'app-skier',
  templateUrl: './skier.component.html',
  styleUrls: ['./skier.component.scss']
})
export class SkierComponent implements OnInit {
  skiers: Skier[] = [];
  skierForm!: FormGroup;
  selectedSkierId: number | null = null;

  constructor(private skierService: SkierService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
    this.loadSkiers();
  }

  initForm(): void {
    this.skierForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      city: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      subscription: this.fb.group({
        startDate: ['', Validators.required],
        typeSub: ['', Validators.required]
      })
    });
  }

  loadSkiers(): void {
    this.skierService.getAllSkiers().subscribe((data) => {
      this.skiers = data;
    });
  }

  addSkier(): void {
  if (this.selectedSkierId) {
    const updatedSkier: Skier = {
      numSkier: this.selectedSkierId,
      ...this.skierForm.value
    };
    this.skierService.updateSkier(updatedSkier).subscribe(() => {
      this.loadSkiers();
      this.skierForm.reset();
      this.selectedSkierId = null;
    });
  } else {
    this.skierService.addSkier(this.skierForm.value).subscribe(() => {
      this.loadSkiers();
      this.skierForm.reset();
    });
  }
}


  deleteSkier(id: number): void {
    this.skierService.deleteSkier(id).subscribe(() => {
      this.loadSkiers();
    });
  }

  editSkier(skier: Skier): void {
    this.selectedSkierId = skier.numSkier || null;
    this.skierForm.patchValue({
      firstName: skier.firstName,
      lastName: skier.lastName,
      city: skier.city,
      dateOfBirth: skier.dateOfBirth,
      subscription: {
        startDate: skier.subscription?.startDate || '',
        typeSub: skier.subscription?.typeSub || ''
      }
    });
  }
}
