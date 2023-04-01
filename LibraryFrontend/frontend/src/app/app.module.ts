import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BookComponent } from './components/book/book.component';
import { LibraryComponent } from './components/library/library.component';
import { ReaderComponent } from './components/reader/reader.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { LibraryService } from './core/service/library.service';
import { BookService } from './core/service/book.service';
import { LibraryDetailsComponent } from './components/library/library-details/library-details.component';
import { BookDetailsComponent } from './components/book/book-details/book-details.component';
import { ReaderDetailsComponent } from './components/reader/reader-details/reader-details.component';
import { BookFilterComponent } from './components/book/book-filter/book-filter.component';
import { FormsModule } from '@angular/forms';
import { LibraryBooksStatisticsComponent } from './components/library/library-books-statistics/library-books-statistics.component';
import { LibraryReadersStatisticsComponent } from './components/library/library-readers-statistics/library-readers-statistics.component';

@NgModule({
  declarations: [
    AppComponent,
    BookComponent,
    LibraryComponent,
    ReaderComponent,
    NavbarComponent,
    FooterComponent,
    MainPageComponent,
    LibraryDetailsComponent,
    BookDetailsComponent,
    ReaderDetailsComponent,
    BookFilterComponent,
    LibraryBooksStatisticsComponent,
    LibraryReadersStatisticsComponent
    // put all components, automat cand le creez
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
    // form module + http service module
  ],
  providers: [
    // all the services
    LibraryService,
    BookService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

// ng g c Book - genereaza o componenta