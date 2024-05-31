package com.forums.api.dto.request.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CategoryCreateRequestDTO {

    @NotEmpty(message = "Categories list must not be empty")
    @Size(min = 1, message = "Categories list must contain at least one element")
    private List<@NotBlank String> categories;
}
