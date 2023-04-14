import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReaderDetails } from 'src/app/core/model/reader.model';
import { ReaderService } from 'src/app/core/service/reader.service';

@Component({
  selector: 'app-reader-details',
  templateUrl: './reader-details.component.html',
  styleUrls: ['./reader-details.component.css']
})
export class ReaderDetailsComponent implements OnInit{
  reader?: ReaderDetails;
  readerID?: string;
  showLoader: boolean = false;

  constructor(private readerService: ReaderService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.showLoader = true;

    this.activatedRoute.params.subscribe(params => {
      this.readerID = params['id'];
      this.readerService.getReader(this.readerID!).subscribe({
        next: (reader: ReaderDetails) => {
          this.reader = reader;
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

  isActive(date: Date): boolean {
    return !(new Date(date).getTime() < new Date().getTime());
  }
}
