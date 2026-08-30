package ru.practicum.explorewithme.service.statisticsClient;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.practicum.explorewithme.stats.client.exception.StatsServerUnavailable;
import ru.practicum.explorewithme.stats.dto.EndpointHitDTO;
import ru.practicum.explorewithme.stats.dto.ViewStatsDTO;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsClient {
    private final DiscoveryClient discoveryClient;
    private final RetryTemplate retryTemplate;
    private final RestClient.Builder restClientBuilder;
    @Value("${stats-server.id}")
    private String statsServiceId;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ServiceInstance getInstance() {
        try {
            return discoveryClient.getInstances(statsServiceId)
                    .getFirst();
        } catch (RuntimeException e) {
            throw new StatsServerUnavailable(
                    "Error discovering statistics service with id " + statsServiceId,
                    e
            );
        }
    }

    private URI makeUri(String path) {
        ServiceInstance instance = retryTemplate.execute(context -> getInstance());
        return URI.create("http://" + instance.getHost() + ":" + instance.getPort() + path);
    }

    public void saveHit(EndpointHitDTO hit) {
        URI uri = makeUri("/hit");
        restClientBuilder.build()
                .post()
                .uri(uri)
                .body(hit)
                .retrieve()
                .toBodilessEntity();

    }

    public ResponseEntity<List<ViewStatsDTO>> getStats(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Boolean unique
    ) {
        URI uri = makeUri("/stats");

        return restClientBuilder
                .build()
                .get()
                .uri(uriBuilder -> {
                    uriBuilder
                            .scheme(uri.getScheme())
                            .host(uri.getHost())
                            .port(uri.getPort())
                            .path(uri.getPath())
                            .queryParam("start", start.format(FORMATTER))
                            .queryParam("end", end.format(FORMATTER));

                    if (uris != null && !uris.isEmpty()) {
                        uriBuilder.queryParam("uris", uris);
                    }

                    if (unique != null) {
                        uriBuilder.queryParam("unique", unique);
                    }

                    return uriBuilder.build();
                })
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<ViewStatsDTO>>() {
                });
    }
}

