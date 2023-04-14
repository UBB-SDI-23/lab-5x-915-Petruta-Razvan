import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, debounceTime } from 'rxjs';
import { Library } from 'src/app/core/model/library.model';
import { Membership, ReaderDetails } from 'src/app/core/model/reader.model';
import { LibraryService } from 'src/app/core/service/library.service';
import { ReaderService } from 'src/app/core/service/reader.service';

@Component({
  selector: 'app-reader-new-membership',
  templateUrl: './reader-new-membership.component.html',
  styleUrls: ['./reader-new-membership.component.css']
})
export class ReaderNewMembershipComponent {
  showSearchLoader: boolean = false;
  showLoader: boolean = false;

  selectedOption?: string;
  selectedLibrary?: Library;
  searchTerm = new Subject<string>();
  options?: Library[];
  reader?: ReaderDetails;

  constructor(private readerService: ReaderService, private libraryService: LibraryService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.showLoader = true;

    this.activatedRoute.params.subscribe(params => {
      this.readerService.getReader(params["id"]).subscribe({
        next: (reader: ReaderDetails) => {
          this.reader = reader;
        },
        error: (error) => {
          console.log(error);
        },
        complete: () => {
          this.showLoader = false;
        }
      });
    });

    this.searchTerm.pipe(
      debounceTime(1000) // debounce by 1 second
    ).subscribe(term => {
      this.showSearchLoader = true;

      if (term.trim()) {
        this.libraryService.getLibrariesByName(term).subscribe({
          next: (libraries: Library[]) => {
            this.options = libraries;
          },
          complete: () => {
            this.showSearchLoader = false;
          }
        });
      } else {
        this.options = undefined;
        this.showSearchLoader = false;
      }
    });
  }

  onSubmit(form: any): void {
    this.showLoader = true;

    if (this.selectedLibrary && this.reader) {
      this.readerService.createMembership(this.selectedLibrary.id.toString(), this.reader.id.toString()).subscribe({
        next: (membership: Membership) => {
          this.router.navigateByUrl("/readers/" + membership.id.readerID);
        },
        error: (error) => {
          console.log(error);
        },
        complete: () => {
          this.showLoader = false;
        }
      });
    }
  }

  onSelection(event: any): void {
    this.selectedOption = event.option.value.name;
    this.selectedLibrary = event.option.value;
  }

  search(term: string): void {
    this.searchTerm.next(term);
  }
}
