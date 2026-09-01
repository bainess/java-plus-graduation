package ru.practicum.explorewithme.shareddto.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserShortDto {
    Long id;
    String name;
}
