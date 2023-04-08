import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/core/model/book.model';
import { BookService } from 'src/app/core/service/book.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})

export class BookComponent implements OnInit {
  books: Book[] = [];

  constructor(private bookService: BookService) {}
  
  ngOnInit(): void {
    this.bookService.getBooks().subscribe((result: Book[]) => {
      this.books = result;
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
}
