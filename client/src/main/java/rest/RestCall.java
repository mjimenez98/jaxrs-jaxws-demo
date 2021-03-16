package rest;

import coreClient.Album;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class RestCall {
    private static final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public static List<Album> GetAlbums() {
        return webClient.get()
                .uri("/albums")
                .retrieve()
                .bodyToFlux(Album.class)
                .collectList()
                .block();
    }

    public static Album GetAlbum(String isrc) {
        return webClient.get()
                .uri("/albums/" + isrc)
                .retrieve()
                .bodyToMono(Album.class)
                .block();
    }

    public static void CreateAlbum(Album album) {
        webClient.post()
                .uri("/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(album), Album.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
