import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatrimonioService } from '../services/matrimonio.service';

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent {
  codiceCoppia: string = '';
  selectedFile: File | null = null;
  message: string = '';
  isLoading: boolean = false;

  constructor(private matrimonioService: MatrimonioService) {}

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      // Verifica dimensione (max 5MB)
      if (file.size > 5 * 1024 * 1024) {
        this.message = 'Errore: Il file supera i 5MB';
        this.selectedFile = null;
        return;
      }
      
      // Verifica tipo file (solo immagini)
      if (!file.type.startsWith('image/')) {
        this.message = 'Errore: Seleziona solo file immagine';
        this.selectedFile = null;
        return;
      }
      
      this.selectedFile = file;
      this.message = '';
    }
  }

  onUpload(): void {
    if (!this.codiceCoppia || !this.selectedFile) {
      this.message = 'Inserisci il codice coppia e seleziona un file';
      return;
    }

    this.isLoading = true;
    this.matrimonioService.caricaFoto(this.codiceCoppia, this.selectedFile)
      .subscribe({
        next: (response) => {
          this.message = 'Foto caricata con successo!';
          this.isLoading = false;
          this.selectedFile = null;
          // Reset file input
          const fileInput = document.getElementById('fileInput') as HTMLInputElement;
          if (fileInput) fileInput.value = '';
        },
        error: (error) => {
          this.message = 'Errore durante il caricamento: ' + (error.error || 'Errore sconosciuto');
          this.isLoading = false;
        }
      });
  }
}
