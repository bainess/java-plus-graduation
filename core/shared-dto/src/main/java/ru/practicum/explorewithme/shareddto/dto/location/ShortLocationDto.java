package ru.practicum.explorewithme.shareddto.dto.location;


import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShortLocationDto {
        Float lat;
        Float lon;
}
