package com.forums.api.mapper;

import com.forums.api.dto.request.thread.ThreadCreateRequestDTO;
import com.forums.api.dto.response.thread.ThreadDTO;
import com.forums.api.entity.Thread;
import com.forums.api.entity.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ThreadMapper {
    public Thread thread_threadCreateRequestDTO(ThreadCreateRequestDTO request, String username) {
        return Thread.builder()
                .title(request.getTitle())
                .createdAt(Instant.now())
                .build();
    }

    public ThreadDTO thread_threadDTO_mapper(Thread thread){
        return ThreadDTO.builder()
                .id(thread.getId())
                .author(thread.getUsername())
                .category(thread.getCategory().getName())
                .createdAt(thread.getCreatedAt())
                .title(thread.getTitle())
                .build();
    }
}
