package com.forums.api.dto.request.thread;

import com.forums.api.dto.request.post.SinglePostCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadCreateRequestDTO {
    private String category;
    private String title;
    SinglePostCreateDTO openingPost;
}
