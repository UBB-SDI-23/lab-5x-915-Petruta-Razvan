import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorIntl, PageEvent } from '@angular/material/paginator';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Library, LibraryAll } from 'src/app/core/model/library.model';
import { StorageService } from 'src/app/core/service/_services/storage.service';
import { UserService } from 'src/app/core/service/_services/user.service';
import { LibraryService } from 'src/app/core/service/library.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})

export class LibraryComponent implements OnInit {
  @ViewChild(MatPaginator) paginator?: MatPaginator;

  libraries: LibraryAll[] = [];

  pageNumber: number = 0;
  pageSize: number = 25;
  noPages: number = 0;
  goToPageNumber: number = 1;

  showLoader: boolean = true;

  roles: string[] = [];
  isLoggedIn = false;
  username?: string;

  constructor(
    private libraryService: LibraryService, 
    private route: ActivatedRoute, 
    private router: Router, 
    private elementRef: ElementRef, 
    private paginatorIntl: MatPaginatorIntl,
    private storageService: StorageService,
    private toastrService: ToastrService,
    private userService: UserService) {}
  
  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      this.username = user.username;
    }

    this.userService.getElementsPerPage().subscribe({
      next: (response) => {
        this.pageSize = response;
      }
    });

    // go back to the top
    window.onscroll = () => this.scrollFunction();

    // customize paginator
    this.paginatorIntl.getRangeLabel = this.getRangeLabel.bind(this.paginatorIntl);

    this.libraryService.countLibraries().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });

    this.listLibraries();
  }

  listLibraries(): void {
    this.showLoader = true;

    this.route.queryParams.subscribe(params => {
      this.pageNumber = Number(params['pageNo']) || 0;
      
      this.userService.getElementsPerPage().subscribe({
        next: (response) => {
          this.pageSize = response;
        },
        complete: () => {
          this.libraryService.getPageLibraries(this.pageNumber, this.pageSize).subscribe({
            next: (result: LibraryAll[]) => {
              this.libraries = result;
            },
            error: (error) => {
              this.showLoader = false;
              this.router.navigateByUrl("/");
              this.toastrService.error(error.error, "", { progressBar: true });
              console.log(error.errror);
            },
            complete: () => {
              this.showLoader = false;
            }
          });
        }
      });
    });
    
    
  }

  onSort(field: string): void {
    const sortByName = ((a: LibraryAll, b: LibraryAll) => {
      return a.name.localeCompare(b.name);
    });

    const sortByAddress = ((a: LibraryAll, b: LibraryAll) => {
      return a.address.localeCompare(b.address);
    });

    const sortByConstructionYear = ((a: LibraryAll, b: LibraryAll) => {
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

  scrollFunction() {
    const mybutton = this.elementRef.nativeElement.querySelector('#btn-back-to-top');

    if (mybutton === null) {
      return;
    }

    if (
      document.body.scrollTop > 200 ||
      document.documentElement.scrollTop > 200
    ) {
      mybutton.style.display = 'block';
    } else {
      mybutton.style.display = 'none';
    }
  }

  backToTop() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }

  onPageChanged(event: PageEvent) {
    this.pageNumber = event.pageIndex;
    this.goToPageNumber = this.pageNumber + 1;
    this.pageSize = event.pageSize;
    
    this.libraryService.countLibraries().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });

    this.router.navigate(['/libraries'], { queryParams: { pageNo: this.pageNumber, pageSize: this.pageSize } })
    .then(() => this.listLibraries());
  }

  goToPage(): void {
    this.pageNumber = Math.min(Math.max(1, this.goToPageNumber), this.noPages) - 1;
    this.router.navigate(['/libraries'], { queryParams: { pageNo: this.pageNumber, pageSize: this.pageSize } })
        .then(() => this.listLibraries());
  }

  checkPageNumber(): void {
    if (this.goToPageNumber >= this.noPages) {
      this.goToPageNumber = this.noPages;
    }
  }

  getRangeLabel(page: number, pageSize: number, length: number): string {
    const total = Math.ceil(length / pageSize);
    return `Page ${page + 1} of ${total}`;
  }

  isUser(): boolean {
    return this.roles.includes("ROLE_USER");
  }

  isUserCorrect(library: Library): boolean {
    return this.username === library.username;
  }

  isModerator(): boolean {
    return this.roles.includes("ROLE_MODERATOR");
  }

  isAdmin(): boolean {
    return this.roles.includes("ROLE_ADMIN");
  }
}
