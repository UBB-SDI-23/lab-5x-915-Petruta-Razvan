import { Component, ElementRef, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ActivatedRoute, Router } from '@angular/router';
import { ReaderAll } from 'src/app/core/model/reader.model';
import { ReaderService } from 'src/app/core/service/reader.service';

@Component({
  selector: 'app-reader',
  templateUrl: './reader.component.html',
  styleUrls: ['./reader.component.css']
})
export class ReaderComponent implements OnInit {
  readers: ReaderAll[] = [];
  pageNumber: number = 0;
  pageSize: number = 25;
  noPages: number = 0;
  showLoader: boolean = true;

  goToPageNumber: number = 0;

  constructor(private readerService: ReaderService, private route: ActivatedRoute, private router: Router, private elementRef: ElementRef) {}
  
  ngOnInit(): void {
    window.onscroll = () => this.scrollFunction();

    this.readerService.countReaders().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });

    this.listReaders();
  }

  listReaders(): void {
    this.showLoader = true;

    this.route.queryParams.subscribe(params => {
      this.pageNumber = Number(params['pageNo']) || 0;
      this.pageSize = Number(params['pageSize']) || 25;
    });

    this.readerService.getPageReaders(this.pageNumber, this.pageSize).subscribe({
      next: (result: ReaderAll[]) => {
        this.readers = result;
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        this.showLoader = false;
      }
    });
  }

  onSort(field: string): void {
    const sortByName = ((a: ReaderAll, b: ReaderAll) => {
      return a.name.localeCompare(b.name);
    });

    const sortByBirthDate = ((a: ReaderAll, b: ReaderAll) => {
      return new Date(a.birthDate).getTime() - new Date(b.birthDate).getTime();
    });

    switch (field) {
      case "name": {
        this.readers.sort(sortByName);
        break;
      }
      case "birthDate": {
        this.readers.sort(sortByBirthDate);
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
    this.goToPageNumber = this.pageNumber;
    this.pageSize = event.pageSize;
    
    this.readerService.countReaders().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });

    this.router.navigate(['/readers'], { queryParams: { pageNo: this.pageNumber, pageSize: this.pageSize } })
    .then(() => this.listReaders());
  }

  goToPage(): void {
    this.pageNumber = Math.min(Math.max(0, this.goToPageNumber), this.noPages);
    const pageIndex = this.pageNumber;
    this.router.navigate(['/readers'], { queryParams: { pageNo: pageIndex, pageSize: this.pageSize } })
        .then(() => this.listReaders());
  }

  checkPageNumber(): void {
    if (this.goToPageNumber > this.noPages) {
      this.goToPageNumber = this.noPages;
    }
  }
}
