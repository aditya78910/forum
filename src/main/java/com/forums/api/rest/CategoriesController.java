package com.forums.api.rest;

import com.forums.api.dto.request.categories.CategoryCreateRequestDTO;
import com.forums.api.dto.response.authentication.UserDTO;
import com.forums.api.dto.response.categories.CategoryCreateResponseDTO;
import com.forums.api.exception.AuthenticationFailedException;
import com.forums.api.exception.ErrorResponse;
import com.forums.api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categories")
@RestController
@AllArgsConstructor
public class CategoriesController {

    CategoryService categoryService;
    @PostMapping
    public ResponseEntity<?> createCategories(@Valid @RequestBody CategoryCreateRequestDTO request,
                                              @AuthenticationPrincipal UserDTO userDTO){

        validateRole(userDTO);

        categoryService.createCategories(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<String> findCategories(){
        return categoryService.getCategories();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteThread(@RequestParam String category,
                                          @AuthenticationPrincipal UserDTO userDTO){

        validateRole(userDTO);

        categoryService.deleteCategory(category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private static void validateRole(UserDTO userDTO) {
        if(ObjectUtils.isEmpty(userDTO.getRoles()) || !userDTO.getRoles().contains("admin")){
            throw new AuthenticationFailedException(ErrorResponse.<String>builder()
                    .message("Operation not Allowed")
                    .build());
        }
    }


}
