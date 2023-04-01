import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookDetails } from 'src/app/core/model/book.model';
import { BookService } from 'src/app/core/service/book.service';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {
  book?: BookDetails
  bookID?: string

  constructor(private bookService: BookService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.bookID = params['id'];
      this.bookService.getBook(this.bookID!).subscribe((book: BookDetails) => {
        this.book = book;
      });
    });
  }
}
