import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddUpdateLibraryDTO, Library, LibraryCount, LibraryDetails } from '../model/library.model';

@Injectable({
  providedIn: 'root'
})
export class LibraryService {
  private baseUrl = "/api/";
  // private baseUrl = "http://13.53.43.81/api/";

  constructor(private httpClient: HttpClient) { }

  getLibraries(): Observable<Library[]> {
    return this.httpClient.get(this.baseUrl + "libraries") as Observable<Library[]>;
  }

  get50Libraries(pageNo: Number, pageSize: Number): Observable<Library[]> {
    return this.httpClient.get(this.baseUrl + "libraries?pageNo=" + pageNo.toString() + "&pageSize=" + pageSize.toString()) as Observable<Library[]>;
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

  countLibraries(): Observable<Number> {
    return this.httpClient.get(this.baseUrl + "libraries/count") as Observable<Number>;
  }

  addLibrary(library: AddUpdateLibraryDTO): Observable<Library> {
    return this.httpClient.post(this.baseUrl + "libraries", library) as Observable<Library>;
  }

  updateLibrary(id: string, library: AddUpdateLibraryDTO): Observable<Library> {
    return this.httpClient.put(this.baseUrl + "libraries/" + id, library) as Observable<Library>;
  }

  deleteLibrary(id: string): Observable<Object> {
    return this.httpClient.delete(this.baseUrl + "libraries/" + id) as Observable<Object>;
  }
}
