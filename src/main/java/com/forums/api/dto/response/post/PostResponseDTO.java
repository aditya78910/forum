package com.forums.api.dto.response.post;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostResponseDTO {
    private String author;
    private String text;
    private Instant createdAt;
}
