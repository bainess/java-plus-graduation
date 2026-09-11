package ru.yandex.practicum.eventservice.category.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCategoryRequest {
    private String name;
}
