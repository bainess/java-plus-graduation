package ru.yandex.practicum.eventservice.category.mapper;

import ru.practicum.explorewithme.shareddto.dto.category.CategoryDto;
import ru.yandex.practicum.eventservice.category.dto.NewCategoryRequest;
import ru.yandex.practicum.eventservice.category.dto.UpdateCategoryRequest;
import ru.yandex.practicum.eventservice.category.model.Category;

public class CategoryMapper {

    public static Category mapToCategory(NewCategoryRequest request) {
        return new Category(
                null,
                request.getName()
        );
    }

    public static Category mapToUpdateCategory(UpdateCategoryRequest request, Category category) {
        category.setName(request.getName());
        return category;
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
