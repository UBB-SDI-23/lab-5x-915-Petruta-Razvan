import { Component, OnInit } from '@angular/core';
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
  pageSize: number = 50; 

  constructor(private bookService: BookService, private route: ActivatedRoute, private router: Router) {}
  
  ngOnInit(): void {
    this.listBooks();
  }

  listBooks(): void {
    this.route.queryParams.subscribe(params => {
      this.pageNumber = Number(params['pageNo']) || 0;
      this.pageSize = Number(params['pageSize']) || 50;
    });

    this.bookService.get50Books(this.pageNumber, this.pageSize).subscribe((result: Book[]) => {
      this.books = result;
    });
  }

  onNext(): void {
    if (this.pageNumber === 19999) {
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
    if (this.pageNumber === 19999) {
      return;
    }

    this.router.navigate(['/books'], { queryParams: { pageNo: 19999, pageSize: this.pageSize } })
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
}
