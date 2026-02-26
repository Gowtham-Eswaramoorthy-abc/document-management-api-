package com.documents.demo.service;


import com.documents.demo.entity.Document;
import com.documents.demo.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    // Upload Document
    public Document uploadDocument(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String filename = file.getOriginalFilename();

        if (filename == null || 
           !(filename.endsWith(".pdf") || filename.endsWith(".txt"))) {
            throw new RuntimeException("Only PDF and TXT files are allowed");
        }

        String content;

        if (filename.endsWith(".txt")) {
            content = new String(file.getBytes(), StandardCharsets.UTF_8);
        } else {
            // For PDF extraction use Apache PDFBox dependency
            content = extractTextFromPdf(file);
        }

        Document document = new Document(
                filename,
                content,
                LocalDateTime.now()
        );

        return documentRepository.save(document);
    }

    // Get Document By ID
    public Document getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    // Delete Document
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new RuntimeException("Document not found");
        }
        documentRepository.deleteById(id);
    }

    // PDF Extraction
    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (var pdf = org.apache.pdfbox.pdmodel.PDDocument.load(file.getInputStream())) {
            var stripper = new org.apache.pdfbox.text.PDFTextStripper();
            return stripper.getText(pdf);
        }
    }
}