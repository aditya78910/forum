package com.forums.api.mapper;

import com.forums.api.dto.response.post.PostResponseDTO;
import com.forums.api.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostResponseDTO post_postresponseDTO_mapper(Post post){

        return PostResponseDTO.builder()
                .text(post.getText())
                .author(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .build();

    }
}
