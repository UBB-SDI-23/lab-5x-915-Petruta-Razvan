import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Library, LibraryCount, LibraryDetails } from '../model/library.model';

@Injectable({
  providedIn: 'root'
})
export class LibraryService {
  private baseUrl = "/api/";

  constructor(private httpClient: HttpClient) { }

  getLibraries(): Observable<Library[]> {
    return this.httpClient.get(this.baseUrl + "libraries") as Observable<Library[]>;
  }

  getLibrary(id: string): Observable<LibraryDetails> {
    return this.httpClient.get(this.baseUrl + "libraries/" + id) as Observable<LibraryDetails>;
  }

  getBooksStatistics(): Observable<LibraryCount[]> {
    return this.httpClient.get(this.baseUrl + "libraries/books-statistic") as Observable<LibraryCount[]>;
  }

  getReadersStatistics(): Observable<LibraryCount[]> {
    return this.httpClient.get(this.baseUrl + "libraries/readers-statistic") as Observable<LibraryCount[]>;
  }
}
