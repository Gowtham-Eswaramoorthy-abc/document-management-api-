package com.documents.demo.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    // Constructors
    public Document() {}

    public Document(String filename, String content, LocalDateTime uploadTime) {
        this.filename = filename;
        this.content = content;
        this.uploadTime = uploadTime;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public LocalDateTime getUploadTime() { return uploadTime; }

    public void setUploadTime(LocalDateTime uploadTime) { this.uploadTime = uploadTime; }
}
