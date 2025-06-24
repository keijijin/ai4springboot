package com.sample.ai_tutorial.controller;

import com.sample.ai_tutorial.service.PdfQaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
public class PdfQaController {

    private final PdfQaService service;

    public PdfQaController(PdfQaService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        service.index(file);
        return ResponseEntity.ok("File uploaded and indexed.");
    }

    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody Map<String, String> body) {
        String question = body.get("question");
        return ResponseEntity.ok(service.ask(question));
    }
}
