package ru.practicum.explorewithme.shareddto.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.shareddto.dto.category.CategoryDto;
import ru.practicum.explorewithme.shareddto.dto.location.ShortLocationDto;
import ru.practicum.explorewithme.shareddto.enums.EventState;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {
    Long id;
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    String createdOn;
    String description;
    String eventDate;
    Long initiator;
    ShortLocationDto location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;
    EventState state;
    String title;
    Long views;
}
