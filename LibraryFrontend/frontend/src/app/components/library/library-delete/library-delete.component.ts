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
  showLoader: boolean = true;

  constructor(private libraryService: LibraryService, private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.showLoader = true;

    this.activatedRoute.params.subscribe(params => {
      this.libraryID = params['id'];
      this.libraryService.getLibrary(this.libraryID!).subscribe({
        next: (result: LibraryDetails) => {
          this.library = result;
        },
        error: (error) => {
          console.log(error);
        },
        complete: () => {
          this.showLoader = false;
        }
      });
    });
  }

  onDelete(): void {
    this.showLoader = true;

    this.libraryService.deleteLibrary(this.libraryID!).subscribe({
      next: (library: Object) => {
        this.router.navigate(['/libraries'], { queryParams: { pageNo: 0, pageSize: 25 } });
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        this.showLoader = false;
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/libraries'], { queryParams: { pageNo: 0, pageSize: 25 } });
  }
}
