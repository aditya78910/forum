package com.forums.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateResponseDTO {
    private String username;
    private String email;
}
