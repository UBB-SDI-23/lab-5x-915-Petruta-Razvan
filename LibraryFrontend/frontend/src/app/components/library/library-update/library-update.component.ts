import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AddUpdateLibraryDTO, Library, LibraryDetails } from 'src/app/core/model/library.model';
import { LibraryService } from 'src/app/core/service/library.service';

@Component({
  selector: 'app-library-update',
  templateUrl: './library-update.component.html',
  styleUrls: ['./library-update.component.css']
})
export class LibraryUpdateComponent implements OnInit {
  libraryID?: string;
  name?: string;
  address?: string;
  email?: string;
  website?: string;
  yearOfConstruction?: number;

  constructor(private libraryService: LibraryService, private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.libraryID = params['id'];
      this.libraryService.getLibrary(this.libraryID!).subscribe((library: LibraryDetails) => {
        this.name = library.name;
        this.address = library.address;
        this.email = library.email;
        this.website = library.website;
        this.yearOfConstruction = library.yearOfConstruction;
      });
    });
  }

  onSubmit(form: any): void {
    if (this.name && this.address && this.email && this.website && this.yearOfConstruction) {
      const library: AddUpdateLibraryDTO = {
        name: this.name,
        address: this.address,
        email: this.email,
        website: this.website,
        yearOfConstruction: this.yearOfConstruction
      }
      
      this.libraryService.updateLibrary(this.libraryID!, library).subscribe({
        next: (library: Library) => {
          this.router.navigateByUrl("/libraries/" + library.id);
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }
}
