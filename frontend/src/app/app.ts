import { Component, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

interface UrlShortenerResponse {
  id: number;
  receivedUrl: string;
  shortenedUrl: string;
}

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  private http = inject(HttpClient);

  url = '';
  shortUrl = signal('');

  shortenUrl() {
    const body = {
      receivedUrl: this.url
    };

    this.http
      .post<UrlShortenerResponse>(
        'http://localhost:8080/urlshortener/post',
        body
      )
      .subscribe(response => {
        this.shortUrl.set(
          'http://localhost:8080/urlshortener/' + response.shortenedUrl
        );
      });
  }
}
