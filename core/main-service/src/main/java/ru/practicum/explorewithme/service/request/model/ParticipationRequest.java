//package ru.practicum.explorewithme.service.request.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import ru.practicum.explorewithme.service.user.model.User;
//import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
//import ru.practicum.explorewithme.shareddto.enums.ParticipationRequestStatus;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "participation_requests",
//        uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "requester_id"}))
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class ParticipationRequest {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    @Column(nullable = false)
//    LocalDateTime created;
//
//    @Column(name = "event_id", nullable = false)
//    private Long eventId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "requester_id", nullable = false)
//    User requester;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    ParticipationRequestStatus status;
//}
