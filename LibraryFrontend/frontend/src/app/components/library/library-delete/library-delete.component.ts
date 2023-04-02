import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Library, LibraryDetails } from 'src/app/core/model/library.model';
import { LibraryService } from 'src/app/core/service/library.service';

@Component({
  selector: 'app-library-delete',
  templateUrl: './library-delete.component.html',
  styleUrls: ['./library-delete.component.css']
})
export class LibraryDeleteComponent implements OnInit {
  library?: LibraryDetails;
  libraryID?: string;
  consent: boolean = false;

  constructor(private libraryService: LibraryService, private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.libraryID = params['id'];
      this.libraryService.getLibrary(this.libraryID!).subscribe((library: LibraryDetails) => {
        this.library = library;
      });
    });
  }

  onDelete(): void {
    this.libraryService.deleteLibrary(this.libraryID!).subscribe({
      next: (library: Object) => {
        this.router.navigateByUrl("/libraries");
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  onCancel(): void {
    this.router.navigateByUrl("/libraries");
  }
}
