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
  reader?: ReaderDetails
  readerID?: string

  constructor(private readerService: ReaderService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.readerID = params['id'];
      this.readerService.getReader(this.readerID!).subscribe((reader: ReaderDetails) => {
        this.reader = reader;
      });
    });
  }
}
