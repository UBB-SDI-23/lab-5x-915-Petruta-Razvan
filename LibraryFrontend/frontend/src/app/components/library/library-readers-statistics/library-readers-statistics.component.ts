import { Component, OnInit } from '@angular/core';
import { LibraryCount } from 'src/app/core/model/library.model';
import { LibraryService } from 'src/app/core/service/library.service';

@Component({
  selector: 'app-library-readers-statistics',
  templateUrl: './library-readers-statistics.component.html',
  styleUrls: ['./library-readers-statistics.component.css']
})
export class LibraryReadersStatisticsComponent implements OnInit {
  libraries: LibraryCount[] = [];

  constructor(private libraryService: LibraryService) {}
  
  ngOnInit(): void {
    this.libraryService.getReadersStatistics().subscribe((result: LibraryCount[]) => {
      this.libraries = result;
    });
  }
}
