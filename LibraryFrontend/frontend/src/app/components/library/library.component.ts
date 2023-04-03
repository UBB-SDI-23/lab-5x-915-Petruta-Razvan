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

  onSortByName(): void {
    this.libraries.sort((a: Library, b: Library) => {
      return a.name.localeCompare(b.name);
    })
  }
}
