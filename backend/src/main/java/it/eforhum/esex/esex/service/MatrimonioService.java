package it.eforhum.esex.esex.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.eforhum.esex.esex.entity.Coppia;
import it.eforhum.esex.esex.entity.Foto;
import it.eforhum.esex.esex.repository.CoppiaRepository;
import it.eforhum.esex.esex.repository.FotoRepository;

@Service
public class MatrimonioService {

    private final CoppiaRepository coppiaRepo;
    private final FotoRepository fotoRepo;

    // Cartella base
    private final Path root = Paths.get("uploads");

    public MatrimonioService(CoppiaRepository coppiaRepo, FotoRepository fotoRepo) {
        this.coppiaRepo = coppiaRepo;
        this.fotoRepo = fotoRepo;
    }

    public void salvaFoto(String codice, MultipartFile file) {
        // 1. Verifichiamo se il matrimonio esiste
        Coppia coppia = coppiaRepo.findByCodice(codice)
                .orElseThrow(() -> new RuntimeException("Codice non valido"));

        try {
            // 2. Definiamo la cartella specifica per questo matrimonio (es. uploads/CODICE)
            Path cartellaMatrimonio = this.root.resolve(codice);

            // 3. Creiamo la cartella se non esiste
            if (!Files.exists(cartellaMatrimonio)) {
                Files.createDirectories(cartellaMatrimonio);
            }

            // 4. Generiamo il nome del file
            String nomeFile = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 5. Salviamo il file nel percorso specifico: uploads/codice/nomefile.jpg
            Files.copy(file.getInputStream(), cartellaMatrimonio.resolve(nomeFile));

            // 6. Salviamo nel DB il percorso relativo (codice/nomefile.jpg)
            Foto foto = new Foto();
            foto.setPath(codice + "/" + nomeFile);
            foto.setCoppia(coppia);

            fotoRepo.save(foto);

        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvataggio fisico del file: " + e.getMessage());
        }

    }

    public List<String> getFotoPerMatrimonio(String codice) {
        // 1. Verifichiamo se il matrimonio esiste
        if (!coppiaRepo.existsByCodice(codice)) {
            throw new RuntimeException("Matrimonio non trovato con il codice: " + codice);
        }

        // 2. Recuperiamo le foto dal database
        List<Foto> listaFoto = fotoRepo.findByCoppiaCodice(codice);

        // 3. Trasformiamo la lista di oggetti "Foto" in una lista di URL (Stringhe)
        // Così Angular riceve direttamente i link alle immagini
        return listaFoto.stream()
                .map(foto -> "/uploads/" + foto.getPath())
                .collect(Collectors.toList());
    }
}