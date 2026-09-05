package ru.practicum.explorewithme.shareddto.dto.location;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {
    Long id;
    String name;
    Float lat;
    Float lon;
    Float radius;
}
