import { Component, OnInit } from '@angular/core';
import { Reader } from 'src/app/core/model/reader.model';
import { ReaderService } from 'src/app/core/service/reader.service';

@Component({
  selector: 'app-reader',
  templateUrl: './reader.component.html',
  styleUrls: ['./reader.component.css']
})
export class ReaderComponent implements OnInit {
  readers: Reader[] = [];

  constructor(private readerService: ReaderService) {}
  
  ngOnInit(): void {
    this.readerService.getReaders().subscribe((result: Reader[]) => {
      this.readers = result;
    });
  }

  onSort(field: string): void {
    const sortByName = ((a: Reader, b: Reader) => {
      return a.name.localeCompare(b.name);
    });

    const sortByBirthDate = ((a: Reader, b: Reader) => {
      return new Date(a.birthDate).getTime() - new Date(b.birthDate).getTime();
    });

    switch (field) {
      case "name": {
        this.readers.sort(sortByName);
        break;
      }
      case "birthDate": {
        this.readers.sort(sortByBirthDate);
        break;
      }
    }
  }
}
