package ru.yandex.practicum.eventservice.location.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.eventservice.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByName(String name);

    Boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    Boolean existsByNameIgnoreCase(String name);
}
