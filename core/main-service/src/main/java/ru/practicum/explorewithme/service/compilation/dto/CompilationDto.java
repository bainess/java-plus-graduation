package ru.practicum.explorewithme.service.compilation.dto;

import lombok.*;
import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;


import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    @Setter
    @Getter
    private List<EventShortDto> events;

}