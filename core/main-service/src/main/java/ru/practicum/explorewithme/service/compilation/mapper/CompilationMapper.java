package ru.practicum.explorewithme.service.compilation.mapper;

import ru.practicum.explorewithme.service.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.service.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.service.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.explorewithme.service.compilation.model.Compilation;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;

import java.util.List;
import java.util.Set;

public final class CompilationMapper {

    public static Compilation toEntity(NewCompilationDto dto) {
        Compilation compilation = new Compilation();
        compilation.setTitle(dto.getTitle());
        compilation.setPinned(dto.getPinned() != null ? dto.getPinned() : false);
        return compilation;
    }

    public static CompilationDto toDto(Compilation compilation, List<EventShortDto> eventShortDtos) {
        CompilationDto comp = CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .build();
        comp.setEvents(eventShortDtos);
        return comp;
    }

    public static void updateEntityFromRequest(UpdateCompilationRequestDto request, Compilation compilation) {
        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
        }
        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }
    }
}