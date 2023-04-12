import { Component, ElementRef, OnInit } from '@angular/core';
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
  pageSize: number = 25;
  noPages: number = 0;
  showLoader: boolean = true;

  constructor(private readerService: ReaderService, private route: ActivatedRoute, private router: Router, private elementRef: ElementRef) {}
  
  ngOnInit(): void {
    window.onscroll = () => this.scrollFunction();

    this.readerService.countReaders().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });

    this.listReaders();
  }

  listReaders(): void {
    this.showLoader = true;

    this.route.queryParams.subscribe(params => {
      this.pageNumber = Number(params['pageNo']) || 0;
      this.pageSize = Number(params['pageSize']) || 25;
    });

    this.readerService.get50Readers(this.pageNumber, this.pageSize).subscribe({
      next: (result: Reader[]) => {
        this.readers = result;
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        this.showLoader = false;
      }
    });
  }

  onNext(): void {
    if (this.pageNumber === this.noPages - 1) {
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
    if (this.pageNumber === this.noPages - 1) {
      return;
    }

    this.router.navigate(['/readers'], { queryParams: { pageNo: this.noPages - 1, pageSize: this.pageSize } })
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

  scrollFunction() {
    const mybutton = this.elementRef.nativeElement.querySelector('#btn-back-to-top');
    if (
      document.body.scrollTop > 200 ||
      document.documentElement.scrollTop > 200
    ) {
      mybutton.style.display = 'block';
    } else {
      mybutton.style.display = 'none';
    }
  }

  backToTop() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }
}
