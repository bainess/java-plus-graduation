package ru.practicum.explorewithme.shareddto.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.shareddto.dto.category.CategoryDto;
import ru.practicum.explorewithme.shareddto.dto.user.UserShortDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {
    Long id;
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    String eventDate;
    Long initiator;
    Boolean paid;
    String title;
    Long views;
}
