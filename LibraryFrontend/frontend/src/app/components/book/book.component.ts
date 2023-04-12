import { Component, ElementRef, OnInit } from '@angular/core';
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

    this.bookService.get50Books(this.pageNumber, this.pageSize).subscribe({
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

  onNext(): void {
    if (this.pageNumber === this.noPages - 1) {
      return;
    }

    this.router.navigate(['/books'], { queryParams: { pageNo: this.pageNumber + 1, pageSize: this.pageSize } })
    .then(() => this.listBooks());
  }

  onPrevious(): void {
    if (this.pageNumber === 0) {
      return;
    }

    this.router.navigate(['/books'], { queryParams: { pageNo: this.pageNumber - 1, pageSize: this.pageSize } })
    .then(() => this.listBooks());
  }

  onStart(): void {
    if (this.pageNumber === 0) {
      return;
    }

    this.router.navigate(['/books'], { queryParams: { pageNo: 0, pageSize: this.pageSize } })
    .then(() => this.listBooks());
  }

  onEnd(): void {
    if (this.pageNumber === this.noPages - 1) {
      return;
    }

    this.router.navigate(['/books'], { queryParams: { pageNo: this.noPages - 1, pageSize: this.pageSize } })
    .then(() => this.listBooks());
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
