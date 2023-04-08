import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book, BookDetails } from '../model/book.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  // private baseUrl = "/api/";
  private baseUrl = "http://13.49.102.150/api/";

  constructor(private httpClient: HttpClient) { }

  getBooks(): Observable<Book[]> {
    return this.httpClient.get(this.baseUrl + "books") as Observable<Book[]>;
  }

  getBook(id: string): Observable<BookDetails> {
    return this.httpClient.get(this.baseUrl + "books/" + id) as Observable<BookDetails>;
  }

  getBookWithMinPrice(price: string): Observable<Book[]> {
    return this.httpClient.get(this.baseUrl + "books?minPrice=" + price) as Observable<Book[]>;
  }
}
