import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LibraryDetails } from 'src/app/core/model/library.model';
import { LibraryService } from 'src/app/core/service/library.service';

@Component({
  selector: 'app-library-details',
  templateUrl: './library-details.component.html',
  styleUrls: ['./library-details.component.css']
})
export class LibraryDetailsComponent implements OnInit {
  library?: LibraryDetails
  libraryID?: string

  constructor(private libraryService: LibraryService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.libraryID = params['id'];
      this.libraryService.getLibrary(this.libraryID!).subscribe((library: LibraryDetails) => {
        this.library = library;
      });
    });
  }
}
