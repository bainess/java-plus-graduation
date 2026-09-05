package ru.practicum.explorewithme.service.compilation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "compilations")
@NoArgsConstructor
@Getter
@Setter
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    private String title;

    @Column(nullable = false)
    private Boolean pinned;

    @ElementCollection
    @CollectionTable(name = "compilation_id", joinColumns = @JoinColumn(name = "compilation_id"))
    @Column(name = "event_id")
    private Set<Long> eventsIds = new HashSet<>();

    public void setEvents(Set<EventShortDto> events) {
        this.eventsIds = events.stream()
                .map(EventShortDto::getId)
                .collect(Collectors.toSet());;
    }
    public Set<Long> getEvents() {
        return this.eventsIds;
    }
}