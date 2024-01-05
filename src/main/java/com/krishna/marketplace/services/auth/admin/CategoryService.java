package com.krishna.marketplace.services.auth.admin;

import java.util.List;

import com.krishna.marketplace.dto.CategoryDto;
import com.krishna.marketplace.model.Category;

public interface CategoryService {
    public Category createCategory(CategoryDto categoryDto);
    public List<Category> getAllCategories();
}
