import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookDetails } from 'src/app/core/model/book.model';
import { BookService } from 'src/app/core/service/book.service';

@Component({
  selector: 'app-book-delete',
  templateUrl: './book-delete.component.html',
  styleUrls: ['./book-delete.component.css']
})
export class BookDeleteComponent implements OnInit {
  book?: BookDetails;
  bookID?: string;
  consent: boolean = false;
  showLoader: boolean = false;

  constructor(private bookService: BookService, private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.showLoader = true;

    this.activatedRoute.params.subscribe(params => {
      this.bookID = params['id'];
      this.bookService.getBook(this.bookID!).subscribe({
        next: (result: BookDetails) => {
          this.book = result;
        },
        error: (error) => {
          console.log(error);
        },
        complete: () => {
          this.showLoader = false;
        }
      });
    });
  }

  onDelete(): void {
    this.bookService.deleteBook(this.bookID!).subscribe({
      next: (library: Object) => {
        this.router.navigate(['/books'], { queryParams: { pageNo: 0, pageSize: 25 } });
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/books'], { queryParams: { pageNo: 0, pageSize: 25 } });
  }
}
