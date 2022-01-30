package com.example.yourtubebackend.service;

import com.example.yourtubebackend.dao.VideoDao;
import com.example.yourtubebackend.dto.FileRangeInfo;
import com.example.yourtubebackend.dto.VideoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT_RANGES;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.CONTENT_RANGE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Service
@AllArgsConstructor
@Slf4j
public class VideoService {

    public static final String VIDEOS_RESOURCE = "videos/";
    public static final String IMAGES_RESOURCE = "images/";
    public static final String VIDEO_FILE_EXTENSION = "mp4";
    public static final String IMAGE_FILE_EXTENSION = "png";
    public static final String VIDEO_CONTENT = "video/";
    public static final String BYTES = "bytes";
    public static final int BYTE_RANGE = 1024;

    private final VideoDao videoDao;

    private final ResourceLoader resourceLoader;

    public List<VideoDto> listVideos() {
        return videoDao.listVideos().stream().map(this::enrichVideoWithEncryptedImage).toList();
    }

    private VideoDto enrichVideoWithEncryptedImage(VideoDto video) {
        Resource resource = resourceLoader.getResource("classpath:/" + IMAGES_RESOURCE + video.getFilename() + "." + IMAGE_FILE_EXTENSION);

        try (InputStream stream = resource.getInputStream()) {
            String encodedImage = Base64.getEncoder().withoutPadding().encodeToString(stream.readAllBytes());
            video.setEncryptedImage(encodedImage);
        } catch (IOException e) {
            log.error("Video image could not be loaded, error message: " + e.getMessage());
        }

        return video;
    }

    public VideoDto getVideo(Long id) {
        VideoDto video = videoDao.getVideo(id);

        return enrichVideoWithEncryptedImage(video);
    }

    @ResponseBody
    public ResponseEntity<byte[]> prepareVideoContent(String filename, String range) {
        log.info("Preparing video content for range: " + range);
        String fullFileName = filename + "." + VIDEO_FILE_EXTENSION;
        FileRangeInfo fileRangeInfo;

        try {
            fileRangeInfo = readByteRange(fullFileName, range);
        } catch (IOException e) {
            log.error("Exception while reading the file with filename: %s, error message: %s".formatted(filename, e.getMessage()));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (range == null) {
            log.info("Video content for video with filename: %s was prepared.".formatted(filename));

            return ResponseEntity.status(HttpStatus.OK)
                    .header(CONTENT_TYPE, VIDEO_CONTENT + VIDEO_FILE_EXTENSION)
                    .header(CONTENT_LENGTH, String.valueOf(fileRangeInfo.fileSize()))
                    .body(fileRangeInfo.byteRange());
        }

        String contentRange = BYTES + " " + fileRangeInfo.rangeStart() + "-" + fileRangeInfo.rangeEnd() + "/" + fileRangeInfo.fileSize();
        log.info("Video content for range %s: for video with filename: %s was prepared.".formatted(range, filename));

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(CONTENT_TYPE, VIDEO_CONTENT + VIDEO_FILE_EXTENSION)
                .header(ACCEPT_RANGES, BYTES)
                .header(CONTENT_LENGTH, String.valueOf(fileRangeInfo.byteRange().length))
                .header(CONTENT_RANGE, contentRange)
                .body(fileRangeInfo.byteRange());
    }

    public FileRangeInfo readByteRange(String filename, String range) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/" + VIDEOS_RESOURCE + filename);

        try (InputStream inputStream = resource.getInputStream(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[BYTE_RANGE];
            int readBytes;
            while ((readBytes = inputStream.read(data, 0, data.length)) != -1) {
                byteArrayOutputStream.write(data, 0, readBytes);
            }
            byteArrayOutputStream.flush();

            byte[] fileBytes = byteArrayOutputStream.toByteArray();
            int fileSize = fileBytes.length;
            int rangeStart = getRangeStart(range);
            int rangeEnd = getRangeEnd(range, fileSize);

            byte[] result = new byte[rangeEnd - rangeStart + 1];

            System.arraycopy(fileBytes, rangeStart, result, 0, result.length);

            return new FileRangeInfo(result, fileSize, rangeStart, rangeEnd);
        }
    }

    private int getRangeStart(String range) {
        return range == null ? 0 : Integer.parseInt(range.split("-")[0].substring(6));
    }

    private int getRangeEnd(String range, int fileSize) {
        if (range == null) {
            return fileSize -1;
        }

        String[] ranges = range.split("-");
        if (ranges.length > 1) {
            int rangeEnd = Integer.parseInt(ranges[1]);
            if (fileSize < rangeEnd) {
                return fileSize - 1;
            }

            return rangeEnd;
        }

        return fileSize -1;
    }
}

