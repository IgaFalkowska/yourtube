package com.example.yourtubebackend.controller;

import com.example.yourtubebackend.dto.VideoDto;
import com.example.yourtubebackend.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
@Slf4j
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<VideoDto>> findVideos() {
        log.info("Request to list videos was received.");
        List<VideoDto> videos = videoService.listVideos();

        log.info("Returning list of videos.");
        return ResponseEntity.ok().headers(getAccessControlAllowOriginHeaders()).body(videos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VideoDto> getVideo(@PathVariable Long id)  {
        log.info("Request to get video with id: %s was received.".formatted(id));
        VideoDto video = videoService.getVideo(id);
        if (video == null) {
            log.error("Video with given id: %s was not found.".formatted(id));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video with given id was not found.");
        }

        log.info("Returning video information for video with id: %s.".formatted(id));
        return ResponseEntity.ok().headers(getAccessControlAllowOriginHeaders()).body(video);
    }

    @GetMapping(value ="/stream/{filename}", produces = "video/mp4")
    public Mono<ResponseEntity<byte[]>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("filename") String filename) {
        log.info("Request for content for video with filename: %s was received.".formatted(filename));
        return Mono.just(videoService.prepareVideoContent(filename, httpRangeList));
    }

    private HttpHeaders getAccessControlAllowOriginHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return responseHeaders;
    }
}
