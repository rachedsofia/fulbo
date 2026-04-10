package com.fulbo.infrastructure.adapter.out.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Client for football-data.org API.
 * Provides live match data, competition info, and player statistics.
 * Free tier: 10 requests/minute.
 */
@Component
public class FootballDataClient {

    private static final Logger log = LoggerFactory.getLogger(FootballDataClient.class);
    private static final String BASE_URL = "https://api.football-data.org/v4";

    @Value("${football-data.api-key:}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private LocalDateTime lastSyncTime;
    private int syncCount;

    public FootballDataClient() {
        this.restTemplate = new RestTemplate();
        this.syncCount = 0;
    }

    /**
     * Sync live matches from football-data.org API.
     * Returns summary of synced data.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> syncLiveMatches() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("timestamp", LocalDateTime.now().toString());

        if (apiKey == null || apiKey.isBlank()) {
            result.put("status", "NO_API_KEY");
            result.put("message", "No se configuró la API key de football-data.org. "
                    + "Configurá la variable FOOTBALL_DATA_API_KEY para sincronizar datos en vivo.");
            result.put("demo_data", getDemoLiveMatches());
            return result;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Auth-Token", apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    BASE_URL + "/matches?status=LIVE,IN_PLAY,PAUSED",
                    HttpMethod.GET, entity, Map.class);

            Map<String, Object> body = response.getBody();
            if (body != null) {
                List<Map<String, Object>> matches = (List<Map<String, Object>>) body.get("matches");
                result.put("status", "OK");
                result.put("matchesFound", matches != null ? matches.size() : 0);
                result.put("matches", matches != null ? matches : List.of());
                syncCount++;
                lastSyncTime = LocalDateTime.now();
            }
        } catch (Exception e) {
            log.error("Error syncing live matches from football-data.org", e);
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
            result.put("demo_data", getDemoLiveMatches());
        }

        return result;
    }

    /**
     * Sync competition data from football-data.org.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> syncCompetitions() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("timestamp", LocalDateTime.now().toString());

        if (apiKey == null || apiKey.isBlank()) {
            result.put("status", "NO_API_KEY");
            result.put("message", "No se configuró la API key. Usando datos de demostración.");
            result.put("competitions", getDemoCompetitions());
            return result;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Auth-Token", apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Argentine Primera División = code BSA
            ResponseEntity<Map> response = restTemplate.exchange(
                    BASE_URL + "/competitions/BSA",
                    HttpMethod.GET, entity, Map.class);

            result.put("status", "OK");
            result.put("competition", response.getBody());
            syncCount++;
            lastSyncTime = LocalDateTime.now();
        } catch (Exception e) {
            log.error("Error syncing competitions", e);
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
            result.put("competitions", getDemoCompetitions());
        }

        return result;
    }

    /**
     * Get API status and sync statistics.
     */
    public Map<String, Object> getApiStatus() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("apiConfigured", apiKey != null && !apiKey.isBlank());
        status.put("baseUrl", BASE_URL);
        status.put("lastSyncTime", lastSyncTime != null ? lastSyncTime.toString() : "Nunca");
        status.put("totalSyncs", syncCount);
        status.put("rateLimitInfo", "Free tier: 10 requests/minuto");
        return status;
    }

    private List<Map<String, Object>> getDemoLiveMatches() {
        List<Map<String, Object>> matches = new ArrayList<>();

        Map<String, Object> match1 = new LinkedHashMap<>();
        match1.put("id", 1);
        match1.put("competition", "Liga Profesional Argentina 2026");
        match1.put("homeTeam", "Boca Juniors");
        match1.put("awayTeam", "River Plate");
        match1.put("score", Map.of("home", 1, "away", 1));
        match1.put("status", "LIVE");
        match1.put("minute", 67);
        matches.add(match1);

        Map<String, Object> match2 = new LinkedHashMap<>();
        match2.put("id", 2);
        match2.put("competition", "Liga Profesional Argentina 2026");
        match2.put("homeTeam", "Racing Club");
        match2.put("awayTeam", "Independiente");
        match2.put("score", Map.of("home", 2, "away", 0));
        match2.put("status", "LIVE");
        match2.put("minute", 45);
        matches.add(match2);

        Map<String, Object> match3 = new LinkedHashMap<>();
        match3.put("id", 3);
        match3.put("competition", "Liga Profesional Argentina 2026");
        match3.put("homeTeam", "San Lorenzo");
        match3.put("awayTeam", "Huracán");
        match3.put("score", Map.of("home", 0, "away", 0));
        match3.put("status", "HALFTIME");
        match3.put("minute", 45);
        matches.add(match3);

        return matches;
    }

    private List<Map<String, Object>> getDemoCompetitions() {
        List<Map<String, Object>> competitions = new ArrayList<>();
        competitions.add(Map.of(
                "id", 1,
                "name", "Liga Profesional Argentina",
                "code", "BSA",
                "area", "Argentina",
                "currentSeason", "2026"
        ));
        competitions.add(Map.of(
                "id", 2,
                "name", "Copa Argentina",
                "code", "CDA",
                "area", "Argentina",
                "currentSeason", "2026"
        ));
        competitions.add(Map.of(
                "id", 3,
                "name", "Copa Libertadores",
                "code", "CLI",
                "area", "Sudamérica",
                "currentSeason", "2026"
        ));
        return competitions;
    }
}
