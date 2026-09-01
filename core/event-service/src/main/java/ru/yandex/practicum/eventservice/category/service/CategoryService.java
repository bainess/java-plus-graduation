package ru.yandex.practicum.eventservice.category.service;


import ru.practicum.explorewithme.shareddto.dto.category.CategoryDto;
import ru.yandex.practicum.eventservice.category.dto.NewCategoryRequest;
import ru.yandex.practicum.eventservice.category.dto.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryRequest request);

    CategoryDto changeCategory(Long catId, UpdateCategoryRequest request);

    void removeCategory(Long catId);

    List<CategoryDto> getAllCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);
}
