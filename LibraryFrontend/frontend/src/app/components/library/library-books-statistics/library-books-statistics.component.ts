import { Component, OnInit } from '@angular/core';
import { LibraryCount } from 'src/app/core/model/library.model';
import { LibraryService } from 'src/app/core/service/library.service';

@Component({
  selector: 'app-library-books-statistics',
  templateUrl: './library-books-statistics.component.html',
  styleUrls: ['./library-books-statistics.component.css']
})
export class LibraryBooksStatisticsComponent implements OnInit {
  libraries: LibraryCount[] = [];

  constructor(private libraryService: LibraryService) {}
  
  ngOnInit(): void {
    this.libraryService.getBooksStatistics().subscribe((result: LibraryCount[]) => {
      this.libraries = result;
    });
  }
}
