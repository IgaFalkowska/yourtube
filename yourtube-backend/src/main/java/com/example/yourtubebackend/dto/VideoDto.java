package com.example.yourtubebackend.dto;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public final class VideoDto {
    private final Long id;
    private final String title;
    private final String description;
    private final String author;
    private final UUID filename;
    private final String[] tags;
    private final OffsetDateTime createdOn;
    private String encryptedImage;

    public VideoDto(Long id, String title, String description, String author, UUID filename, String[] tags, OffsetDateTime createdOn) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.filename = filename;
        this.tags = tags;
        this.createdOn = createdOn;
    }

    public void setEncryptedImage(String encryptedImage) {
        this.encryptedImage = encryptedImage;
    }
}
