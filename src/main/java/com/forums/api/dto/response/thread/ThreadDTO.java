package com.forums.api.dto.response.thread;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ThreadDTO {
    private Long id;
    private String category;
    private String title;
    private String author;
    private Instant createdAt;
}
