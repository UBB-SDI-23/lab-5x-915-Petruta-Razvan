import { Component, OnInit } from '@angular/core';
import { Library } from 'src/app/core/model/library.model';
import { LibraryService } from 'src/app/core/service/library.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})

export class LibraryComponent implements OnInit {
  libraries: Library[] = [];

  constructor(private libraryService: LibraryService) {}
  
  ngOnInit(): void {
    this.libraryService.getLibraries().subscribe((result: Library[]) => {
      this.libraries = result;
    });
  }

  onSort(field: string): void {
    const sortByName = ((a: Library, b: Library) => {
      return a.name.localeCompare(b.name);
    });

    const sortByAddress = ((a: Library, b: Library) => {
      return a.address.localeCompare(b.address);
    });

    const sortByConstructionYear = ((a: Library, b: Library) => {
      return a.yearOfConstruction - b.yearOfConstruction;
    });

    switch (field) {
      case "name": {
        this.libraries.sort(sortByName);
        break;
      }
      case "address": {
        this.libraries.sort(sortByAddress);
        break;
      }
      case "constructionYear": {
        this.libraries.sort(sortByConstructionYear);
        break;
      }
    }
  }
}
