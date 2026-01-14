package it.eforhum.esex.esex.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eforhum.esex.esex.service.MatrimonioService;

@RestController
@RequestMapping("/matrimonio")
public class MatrimonioController {

    private final MatrimonioService matrimonioService;

    public MatrimonioController(MatrimonioService matrimonioService) {
        this.matrimonioService = matrimonioService;
    }

    @PostMapping("/{codiceCoppia}/carica")
    public ResponseEntity<String> caricaFoto(@PathVariable String codiceCoppia,
            @RequestParam("file") MultipartFile file) {
        try {
            matrimonioService.salvaFoto(codiceCoppia, file);
            return ResponseEntity.ok("Foto caricata con successo.");
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR)
                    .body("Errore durante il caricamento della foto.");
        }
    }


    @GetMapping("/{codice}/gallery")
    public ResponseEntity<List<String>> getGallery(@PathVariable String codice) {
        try {
            List<String> urls = matrimonioService.getFotoPerMatrimonio(codice);
            return ResponseEntity.ok(urls);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}
