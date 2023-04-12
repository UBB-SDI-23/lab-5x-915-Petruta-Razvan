import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReaderDetails } from 'src/app/core/model/reader.model';
import { ReaderService } from 'src/app/core/service/reader.service';

@Component({
  selector: 'app-reader-delete',
  templateUrl: './reader-delete.component.html',
  styleUrls: ['./reader-delete.component.css']
})
export class ReaderDeleteComponent implements OnInit {
  reader?: ReaderDetails;
  readerID?: string;
  consent: boolean = false;
  showLoader: boolean = false;

  constructor(private readerService: ReaderService, private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.showLoader = true;

    this.activatedRoute.params.subscribe(params => {
      this.readerID = params['id'];
      this.readerService.getReader(this.readerID!).subscribe({
        next: (result: ReaderDetails) => {
          this.reader = result;
        },
        error: (error) => {
          console.log(error);
        },
        complete: () => {
          this.showLoader = false;
        }
      });
    });
  }

  onDelete(): void {
    this.showLoader = true;

    this.readerService.deleteReader(this.readerID!).subscribe({
      next: (reader: Object) => {
        this.router.navigate(['/readers'], { queryParams: { pageNo: 0, pageSize: 25 } });
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        this.showLoader = false;
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/readers'], { queryParams: { pageNo: 0, pageSize: 25 } });
  }
}
