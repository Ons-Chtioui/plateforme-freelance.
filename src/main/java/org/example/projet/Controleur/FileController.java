package org.example.projet.Controleur;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/uploads")
public class FileController {

    private static final String UPLOAD_DIR = "uploads/";


    @GetMapping("/photos/{filename:.+}")
    public ResponseEntity<Resource> servePhoto(@PathVariable String filename) {
        try {
            Path file = Paths.get(UPLOAD_DIR + "photos/" + filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Impossible de lire le fichier: " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur: " + e.getMessage());
        }
    }


    @GetMapping("/cv/{filename:.+}")
    public ResponseEntity<Resource> serveCv(@PathVariable String filename) {
        try {
            Path file = Paths.get(UPLOAD_DIR + "cv/" + filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                        .body(resource);
            } else {
                throw new RuntimeException("Impossible de lire le fichier: " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur: " + e.getMessage());
        }
    }
}
