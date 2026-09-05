//package ru.practicum.explorewithme.service.user.feign.fallback;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.openfeign.FallbackFactory;
//import org.springframework.stereotype.Component;
//import ru.practicum.explorewithme.service.user.feign.EventClient;
//import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;
//import ru.practicum.explorewithme.shareddto.exception.EventServiceNotAvailableException;
//
//import java.util.List;
//
//@Component
//public class EventClientFallback implements FallbackFactory<EventClient> {
//    private static final Logger log = LoggerFactory.getLogger(EventClientFallback.class);
//    @Override
//    public EventClient create(Throwable cause) {
//        return new EventClient() {
//            @Override
//            public List<EventShortDto> getEventsByUser(Long userId) {
//                log.info("Event client is not available for user service userId={}",userId,cause);
//                throw new EventServiceNotAvailableException(userId, cause);
//            }
//        };
//    }
//}
