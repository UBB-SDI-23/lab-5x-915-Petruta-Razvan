import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Reader, ReaderDetails } from '../model/reader.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReaderService {
  // private baseUrl = "/api/";
  private baseUrl = "http://13.49.102.150/api/";

  constructor(private httpClient: HttpClient) { }

  getReaders(): Observable<Reader[]> {
    return this.httpClient.get(this.baseUrl + "readers") as Observable<Reader[]>;
  }

  getReader(id: string): Observable<ReaderDetails> {
    return this.httpClient.get(this.baseUrl + "readers/" + id) as Observable<ReaderDetails>;
  }
}
