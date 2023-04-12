import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/core/model/book.model';
import { BookService } from 'src/app/core/service/book.service';

@Component({
  selector: 'app-book-filter',
  templateUrl: './book-filter.component.html',
  styleUrls: ['./book-filter.component.css']
})
export class BookFilterComponent implements OnInit {
  books: Book[] = [];
  searchTerm: string = '0';
  showLoader: boolean = false;

  constructor(private bookService: BookService) {}
  
  ngOnInit(): void {
    this.onSearch();
  }

  onSearch(): void {
    this.showLoader = true;

    this.bookService.getBookWithMinPrice(this.searchTerm).subscribe({
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
}
