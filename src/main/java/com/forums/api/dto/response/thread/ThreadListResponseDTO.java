package com.forums.api.dto.response.thread;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ThreadListResponseDTO {
    List<ThreadDTO> threads;
}
