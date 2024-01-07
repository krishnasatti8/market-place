package com.krishna.marketplace.services.admin;

import java.util.List;

import com.krishna.marketplace.dto.CategoryDto;
import com.krishna.marketplace.model.Category;

public interface AdminCategoryService {
    public Category createCategory(CategoryDto categoryDto);
    public List<Category> getAllCategories();
}
