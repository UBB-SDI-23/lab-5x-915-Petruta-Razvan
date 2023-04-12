import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AddUpdateReaderDTO, Reader, ReaderDetails } from 'src/app/core/model/reader.model';
import { ReaderService } from 'src/app/core/service/reader.service';

@Component({
  selector: 'app-reader-update',
  templateUrl: './reader-update.component.html',
  styleUrls: ['./reader-update.component.css']
})
export class ReaderUpdateComponent implements OnInit {
  readerID?: string;
  name?: string;
  email?: string;
  birthDate?: Date;
  gender?: string;
  student?: boolean;
  showLoader: boolean = false;
  errorMessages = {
    'name': null,
    'email': null,
    'birthDate': null,
    'gender': null,
    'student': null
  };

  constructor(private readerService: ReaderService, private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.showLoader = true;

    this.activatedRoute.params.subscribe(params => {
      this.readerID = params['id'];
      this.readerService.getReader(this.readerID!).subscribe({
        next: (reader: ReaderDetails) => {
          this.name = reader.name;
          this.email = reader.email;
          this.birthDate = reader.birthDate;
          this.gender = reader.gender;
          this.student = reader.student;
        },
        error: (error) => {
          console.log(error);
        },
        complete: () => {
          this.showLoader = false;
        }
      }
    );
    });
  }

  onSubmit(form: any): void {
    this.showLoader = false;

    if (this.name && this.email && this.gender && this.birthDate && this.student !== undefined) {
      const reader: AddUpdateReaderDTO = {
        name: this.name,
        email: this.email,
        birthDate: this.birthDate,
        gender: this.gender,
        student: this.student
      }
      
      this.readerService.updateReader(this.readerID!, reader).subscribe({
        next: (reader: Reader) => {
          this.router.navigateByUrl("/readers/" + reader.id);
        },
        error: (error) => {
          this.showLoader = false;

          if (error.status === 400) {
            const errors = error.error.errors;

            if ('name' in errors) {
              this.errorMessages.name = errors.name;
            } else {
              this.errorMessages.name = null;
            }

            if ('email' in errors) {
              this.errorMessages.email = errors.email;
            } else {
              this.errorMessages.email = null;
            }

            if ('gender' in errors) {
              this.errorMessages.gender = errors.gender;
            } else {
              this.errorMessages.gender = null;
            }

            if ('birthDate' in errors) {
              this.errorMessages.birthDate = errors.birthDate;
            } else {
              this.errorMessages.birthDate = null;
            }

            if ('student' in errors) {
              this.errorMessages.student = errors.student;
            } else {
              this.errorMessages.student = null;
            }
          }
        },
        complete: () => {
          this.showLoader = false;
        }
      });
    }
  }
}
