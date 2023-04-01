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
}
