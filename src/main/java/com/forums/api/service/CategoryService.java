package com.forums.api.service;

import com.forums.api.dto.request.categories.CategoryCreateRequestDTO;
import com.forums.api.dto.response.categories.CategoryCreateResponseDTO;
import com.forums.api.entity.Category;
import com.forums.api.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private static final String DEFAULT_CATEGORY = "Default";
    CategoryRepository categoryRepository;
    public void createCategories(CategoryCreateRequestDTO request) {
        List<Category> categoryList = request.getCategories().stream()
                .map(categoryName -> Category.builder()
                        .name(categoryName)
                        .build()).toList();

        categoryRepository.saveAll(categoryList);
    }

    public List<String> getCategories() {
        return categoryRepository.findAll()
                .stream().map(Category::getName)
                .toList();
    }

    public void deleteCategory(String categoryName) {
        if(categoryName.equals(DEFAULT_CATEGORY)){
            throw new RuntimeException("cannot delete default category");
        }

        Category category = categoryRepository.findByName(categoryName)
                                .orElseThrow();

        categoryRepository.deleteById(category.getId());
    }
}
