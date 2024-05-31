package com.forums.api.dto.request.thread;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ThreadGetRequest {
    @NotEmpty(message = "Categories list must not be empty")
    @Size(min = 1, message = "Categories list must contain at least one element")
    private List<String> categories;
    private int page = 0;
    private int page_size = 10;
    private boolean newest_first;
}
