import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Reader } from 'src/app/core/model/reader.model';
import { ReaderService } from 'src/app/core/service/reader.service';

@Component({
  selector: 'app-reader',
  templateUrl: './reader.component.html',
  styleUrls: ['./reader.component.css']
})
export class ReaderComponent implements OnInit {
  readers: Reader[] = [];
  pageNumber: number = 0;
  pageSize: number = 50;

  constructor(private readerService: ReaderService, private route: ActivatedRoute, private router: Router) {}
  
  ngOnInit(): void {
    this.listReaders();
  }

  listReaders(): void {
    this.route.queryParams.subscribe(params => {
      this.pageNumber = Number(params['pageNo']) || 0;
      this.pageSize = Number(params['pageSize']) || 50;
    });

    this.readerService.get50Readers(this.pageNumber, this.pageSize).subscribe((result: Reader[]) => {
      this.readers = result;
    });
  }

  onNext(): void {
    if (this.pageNumber === 19999) {
      return;
    }

    this.router.navigate(['/readers'], { queryParams: { pageNo: this.pageNumber + 1, pageSize: this.pageSize } })
    .then(() => this.listReaders());
  }

  onPrevious(): void {
    if (this.pageNumber === 0) {
      return;
    }

    this.router.navigate(['/readers'], { queryParams: { pageNo: this.pageNumber - 1, pageSize: this.pageSize } })
    .then(() => this.listReaders());
  }

  onStart(): void {
    if (this.pageNumber === 0) {
      return;
    }

    this.router.navigate(['/readers'], { queryParams: { pageNo: 0, pageSize: this.pageSize } })
    .then(() => this.listReaders());
  }

  onEnd(): void {
    if (this.pageNumber === 19999) {
      return;
    }

    this.router.navigate(['/readers'], { queryParams: { pageNo: 19999, pageSize: this.pageSize } })
    .then(() => this.listReaders());
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
