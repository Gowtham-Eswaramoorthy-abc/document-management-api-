package com.documents.demo.controller;

import com.documents.demo.entity.Document;
import com.documents.demo.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // 1️⃣ Upload
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Document> uploadDocument(
        @RequestParam("file") MultipartFile file) throws Exception {

        Document saved = documentService.uploadDocument(file);
        return ResponseEntity.ok(saved);
    }

    // 2️⃣ Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocument(id));
    }

    // 3️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok("Document deleted successfully");
    }
}
