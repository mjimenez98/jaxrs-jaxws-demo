package calls;

import coreClient.Album;
import coreClient.Cover;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RestCalls {
    private static final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

//    ALBUM

    public static List<Album> ListAlbums() {
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
        try {
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

            // Set file - DOES NOT WORK
            formData.add("file", album.getCover().getBlobAsResource());

            // Set album
            album.setCover(new Cover());
            formData.add("album", album);

            webClient.post()
                    .uri("/albums")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(formData))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void EditAlbum(Album album) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        album.setCover(new Cover());
        formData.add("album", album);

        webClient.put()
                .uri("/albums")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public static void DeleteAlbum(String isrc) {
         webClient.delete()
                .uri("/albums/" + isrc)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

//    ALBUM COVER

    public static void GetAlbumCover() {
        // TO BE ADDED
    }

    public static void EditAlbumCover(Album album) {
        // TO BE ADDED
    }

    public static void DeleteAlbumCover(String isrc) {
        webClient.delete()
                .uri("/albums/cover/" + isrc)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

//    HELPERS

    // DOES NOT WORK
    public static Resource getTestFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        Files.write(testFile, "Hello World !!, This is a test file.".getBytes());

        return new FileSystemResource(testFile.toFile());
    }
}
