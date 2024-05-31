package com.forums.api.dto.response.post;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ThreadPostResponseDTO {
    private Long id;
    private String category;
    private String title;
    private String author;
    private Instant createdAt;
    private List<PostResponseDTO> posts;
}
