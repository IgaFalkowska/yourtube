package com.example.yourtubebackend.dao;

import com.example.yourtubebackend.dto.VideoDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.yourtubebackend.jooq.Tables.SYSTEM_USER;
import static com.example.yourtubebackend.jooq.Tables.VIDEO;

@Component
@RequiredArgsConstructor
public class VideoDao {
    private final DSLContext dslContext;

    public VideoDto getVideo(Long id) {
        Record record = dslContext.select()
                .from(VIDEO.join(SYSTEM_USER).on(VIDEO.SYSTEM_USER_ID.eq(SYSTEM_USER.ID)))
                .where(VIDEO.ID.eq(id))
                .fetchOne();

        if (record == null) {
            return null;
        }

        return new VideoDto(
                record.get(VIDEO.ID),
                record.get(VIDEO.TITLE),
                record.get(VIDEO.DESCRIPTION),
                record.get(SYSTEM_USER.NAME),
                record.get(VIDEO.FILENAME),
                record.get(VIDEO.TAGS),
                record.get(VIDEO.CREATED_ON)
        );
    }

    public List<VideoDto> listVideos() {
        return dslContext.select()
                .from(VIDEO.join(SYSTEM_USER).on(VIDEO.SYSTEM_USER_ID.eq(SYSTEM_USER.ID)))
                .fetch()
                .map(record -> new VideoDto(
                        record.get(VIDEO.ID),
                        record.get(VIDEO.TITLE),
                        record.get(VIDEO.DESCRIPTION),
                        record.get(SYSTEM_USER.NAME),
                        record.get(VIDEO.FILENAME),
                        record.get(VIDEO.TAGS),
                        record.get(VIDEO.CREATED_ON)
                ));
    }
}
