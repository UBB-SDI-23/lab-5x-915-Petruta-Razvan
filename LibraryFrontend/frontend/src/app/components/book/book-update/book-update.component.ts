import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Book, BookDetails, UpdateBookDTO } from 'src/app/core/model/book.model';
import { BookService } from 'src/app/core/service/book.service';

@Component({
  selector: 'app-book-update',
  templateUrl: './book-update.component.html',
  styleUrls: ['./book-update.component.css']
})
export class BookUpdateComponent implements OnInit {
  bookID?: string;
  title?: string;
  author?: string;
  publisher?: string;
  price?: number;
  publishedYear?: number;
  description?: string;
  showLoader: boolean = false;

  constructor(private bookService: BookService, private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.showLoader = true;

    this.activatedRoute.params.subscribe(params => {
      this.bookID = params['id'];
      this.bookService.getBook(this.bookID!).subscribe({
        next: (book: BookDetails) => {
          this.title = book.title;
          this.author = book.author;
          this.publisher = book.publisher;
          this.price = book.price;
          this.publishedYear = book.publishedYear;
          this.description = book.description;
        },
        error: (error) => {
          console.log(error);
        },
        complete: () => {
          this.showLoader = false;
        }
      }
    );
    });
  }

  onSubmit(form: any): void {
    if (this.title && this.author && this.publisher && this.description && this.publishedYear && this.description && this.price) {
      const book: UpdateBookDTO = {
        title: this.title,
        author: this.author,
        price: this.price,
        description: this.description,
        publisher: this.publisher,
        publishedYear: this.publishedYear
      }
      
      this.bookService.updateBook(this.bookID!, book).subscribe({
        next: (book: Book) => {
          this.router.navigateByUrl("/books/" + book.id);
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }
}
