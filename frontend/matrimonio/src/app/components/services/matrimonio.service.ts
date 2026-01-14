import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MatrimonioService {
  private apiUrl = 'http://localhost:8080/matrimonio';
  private apiKey = 'Matrimonio-Segreto-2026-XYZ123';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'X-API-KEY': this.apiKey
    });
  }

  caricaFoto(codiceCoppia: string, file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<string>(
      `${this.apiUrl}/${codiceCoppia}/carica`,
      formData,
      { headers: this.getHeaders(), responseType: 'text' as 'json' }
    );
  }

  getGallery(codice: string): Observable<string[]> {
    return this.http.get<string[]>(
      `${this.apiUrl}/${codice}/gallery`,
      { headers: this.getHeaders() }
    );
  }
}
