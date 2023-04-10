import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Library } from 'src/app/core/model/library.model';
import { LibraryService } from 'src/app/core/service/library.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})

export class LibraryComponent implements OnInit {
  libraries: Library[] = [];
  pageNumber: number = 0;
  pageSize: number = 50;
  noElements: number = 0;

  constructor(private libraryService: LibraryService, private route: ActivatedRoute, private router: Router, private elementRef: ElementRef) {}
  
  ngOnInit(): void {
    window.onscroll = () => this.scrollFunction();

    this.libraryService.countLibraries().subscribe((result: Number) => {
      this.noElements = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noElements++;
      }
    });

    this.listLibraries();
  }

  listLibraries(): void {
    this.route.queryParams.subscribe(params => {
      this.pageNumber = Number(params['pageNo']) || 0;
      this.pageSize = Number(params['pageSize']) || 50;
    });
    
    this.libraryService.get50Libraries(this.pageNumber, this.pageSize).subscribe((result: Library[]) => {
      this.libraries = result;
    });
  }

  onNext(): void {
    if (this.pageNumber === this.noElements - 1) {
      return;
    }

    this.router.navigate(['/libraries'], { queryParams: { pageNo: this.pageNumber + 1, pageSize: this.pageSize } })
    .then(() => this.listLibraries());
  }

  onPrevious(): void {
    if (this.pageNumber === 0) {
      return;
    }

    this.router.navigate(['/libraries'], { queryParams: { pageNo: this.pageNumber - 1, pageSize: this.pageSize } })
    .then(() => this.listLibraries());
  }

  onStart(): void {
    if (this.pageNumber === 0) {
      return;
    }

    this.router.navigate(['/libraries'], { queryParams: { pageNo: 0, pageSize: this.pageSize } })
    .then(() => this.listLibraries());
  }

  onEnd(): void {
    if (this.pageNumber === this.noElements - 1) {
      return;
    }

    this.router.navigate(['/libraries'], { queryParams: { pageNo: this.noElements - 1, pageSize: this.pageSize } })
    .then(() => this.listLibraries());
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

  scrollFunction() {
    const mybutton = this.elementRef.nativeElement.querySelector('#btn-back-to-top');
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
}
