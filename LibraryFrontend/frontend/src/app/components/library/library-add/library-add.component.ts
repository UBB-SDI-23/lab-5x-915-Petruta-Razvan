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

  constructor(private libraryService: LibraryService, private router: Router) {}

  onSubmit(form: any) {
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
          console.log(error);
        }
      })
    }
  }
}
