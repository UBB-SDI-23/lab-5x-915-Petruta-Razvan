import { Component, ElementRef, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from 'src/app/core/model/book.model';
import { BookService } from 'src/app/core/service/book.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})

export class BookComponent implements OnInit {
  books: Book[] = [];
  pageNumber: number = 0;
  pageSize: number = 25; 
  noPages: number = 0;
  showLoader: boolean = false;

  goToPageNumber: number = 0;

  constructor(private bookService: BookService, private route: ActivatedRoute, private router: Router, private elementRef: ElementRef) {}
  
  ngOnInit(): void {
    window.onscroll = () => this.scrollFunction();

    this.bookService.countBooks().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });

    this.listBooks();
  }

  listBooks(): void {
    this.showLoader = true;

    this.route.queryParams.subscribe(params => {
      this.pageNumber = Number(params['pageNo']) || 0;
      this.pageSize = Number(params['pageSize']) || 50;
    });

    this.bookService.getPageBooks(this.pageNumber, this.pageSize).subscribe({
      next: (result: Book[]) => {
        this.books = result;
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
    const sortByTitle = ((a: Book, b: Book) => {
      return a.title.localeCompare(b.title);
    });

    const sortByPrice = ((a: Book, b: Book) => {
      return a.price - b.price;
    });

    const sortByPublishedYear = ((a: Book, b: Book) => {
      return a.publishedYear - b.publishedYear;
    });

    switch (field) {
      case "title": {
        this.books.sort(sortByTitle);
        break;
      }
      case "price": {
        this.books.sort(sortByPrice);
        break;
      }
      case "publishedYear": {
        this.books.sort(sortByPublishedYear);
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
    
    this.bookService.countBooks().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });

    this.router.navigate(['/books'], { queryParams: { pageNo: this.pageNumber, pageSize: this.pageSize } })
    .then(() => this.listBooks());
  }

  goToPage(): void {
    this.pageNumber = Math.min(Math.max(0, this.goToPageNumber), this.noPages);
    const pageIndex = this.pageNumber;
    this.router.navigate(['/books'], { queryParams: { pageNo: pageIndex, pageSize: this.pageSize } })
        .then(() => this.listBooks());
  }

  checkPageNumber(): void {
    if (this.goToPageNumber > this.noPages) {
      this.goToPageNumber = this.noPages;
    }
  }
}
