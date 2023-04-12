import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AddUpdateLibraryDTO, Library } from 'src/app/core/model/library.model';
import { LibraryService } from 'src/app/core/service/library.service';

@Component({
  selector: 'app-library-add',
  templateUrl: './library-add.component.html',
  styleUrls: ['./library-add.component.css']
})
export class LibraryAddComponent {
  name?: string;
  address?: string;
  email?: string;
  website?: string;
  yearOfConstruction?: number;
  showLoader: boolean = false;
  errorMessages = {
    'name': null,
    'address': null,
    'email': null,
    'website': null,
    'yearOfConstruction': null
  };

  constructor(private libraryService: LibraryService, private router: Router) {}

  onSubmit(form: any) {
    this.showLoader = true;

    if (this.name && this.address && this.email && this.website && this.yearOfConstruction) {
      const library: AddUpdateLibraryDTO = {
        name: this.name,
        address: this.address,
        email: this.email,
        website: this.website,
        yearOfConstruction: this.yearOfConstruction
      }
      
      this.libraryService.addLibrary(library).subscribe({
        next: (library: Library) => {
          this.router.navigateByUrl("/libraries/" + library.id);
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

            if ('address' in errors) {
              this.errorMessages.address = errors.address;
            } else {
              this.errorMessages.address = null;
            }

            if ('website' in errors) {
              this.errorMessages.website = errors.website;
            } else {
              this.errorMessages.website = null;
            }

            if ('yearOfConstruction' in errors) {
              this.errorMessages.yearOfConstruction = errors.yearOfConstruction;
            } else {
              this.errorMessages.yearOfConstruction = null;
            }
          }
        },
        complete: () => {
          this.showLoader = false;
        }
      })
    }
  }
}
