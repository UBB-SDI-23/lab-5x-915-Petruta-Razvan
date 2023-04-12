import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddUpdateReaderDTO, Reader, ReaderDetails } from '../model/reader.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReaderService {
  // private baseUrl = "/api/";
  private baseUrl = "http://localhost/api/";

  constructor(private httpClient: HttpClient) { }

  getReaders(): Observable<Reader[]> {
    return this.httpClient.get(this.baseUrl + "readers") as Observable<Reader[]>;
  }

  get50Readers(pageNo: Number, pageSize: Number): Observable<Reader[]> {
    return this.httpClient.get(this.baseUrl + "readers?pageNo=" + pageNo.toString() + "&pageSize=" + pageSize.toString()) as Observable<Reader[]>;
  }

  getReader(id: string): Observable<ReaderDetails> {
    return this.httpClient.get(this.baseUrl + "readers/" + id) as Observable<ReaderDetails>;
  }

  countReaders(): Observable<Number> {
    return this.httpClient.get(this.baseUrl + "readers/count") as Observable<Number>;
  }

  addReader(reader: AddUpdateReaderDTO): Observable<Reader> {
    return this.httpClient.post(this.baseUrl + "readers", reader) as Observable<Reader>;
  }

  updateReader(id: string, reader: AddUpdateReaderDTO): Observable<Reader> {
    return this.httpClient.put(this.baseUrl + "readers/" + id, reader) as Observable<Reader>;
  }

  deleteReader(id: string): Observable<Object> {
    return this.httpClient.delete(this.baseUrl + "readers/" + id) as Observable<Object>;
  }
}
