package com.example.yourtubebackend.dto;

public record FileRangeInfo(byte[] byteRange, int fileSize, int rangeStart, int rangeEnd) {}
