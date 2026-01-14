import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatrimonioService } from '../services/matrimonio.service';

@Component({
  selector: 'app-gallery',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent {
  codiceCoppia: string = '';
  fotoUrls: string[] = [];
  message: string = '';
  isLoading: boolean = false;

  constructor(private matrimonioService: MatrimonioService) {}

  caricaGallery(): void {
    if (!this.codiceCoppia) {
      this.message = 'Inserisci il codice della coppia';
      return;
    }

    this.isLoading = true;
    this.message = '';
    this.matrimonioService.getGallery(this.codiceCoppia)
      .subscribe({
        next: (urls) => {
          this.fotoUrls = urls;
          this.isLoading = false;
          if (urls.length === 0) {
            this.message = 'Nessuna foto trovata per questo codice';
          }
        },
        error: (error) => {
          this.message = 'Errore: Coppia non trovata o errore nel caricamento';
          this.fotoUrls = [];
          this.isLoading = false;
        }
      });
  }

  getFullUrl(path: string): string {
    return `http://localhost:8080${path}`;
  }
}
