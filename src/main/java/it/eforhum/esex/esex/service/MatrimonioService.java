package it.eforhum.esex.esex.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
    
    private final Path root = Paths.get("uploads");

    public MatrimonioService(CoppiaRepository coppiaRepo, FotoRepository fotoRepo) {
        this.coppiaRepo = coppiaRepo;
        this.fotoRepo = fotoRepo;
        
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile creare la cartella di upload");
        }
    }

    public void salvaFoto(String codice, MultipartFile file) {
        
        Coppia coppia = coppiaRepo.findByCodice(Long.parseLong(codice))
                .orElseThrow(() -> new RuntimeException("Coppia non trovata"));

        try {
           
            String nomeFile = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            
           
            Files.copy(file.getInputStream(), this.root.resolve(nomeFile));

           
            Foto foto = new Foto();
            foto.setPath(nomeFile); 
            foto.setCoppia(coppia);
            
            fotoRepo.save(foto);
            
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il salvataggio del file: " + e.getMessage());
        }
    }
}