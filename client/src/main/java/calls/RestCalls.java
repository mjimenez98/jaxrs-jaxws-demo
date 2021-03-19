package calls;

import coreClient.Album;
import coreClient.Cover;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
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

            // Convert file to ByteArrayResource
            String filename = album.getCover().getBlob().getOriginalFilename();
            ByteArrayResource contentsAsResource = new ByteArrayResource(album.getCover().getBlob().getBytes()) {
                @Override
                public String getFilename() {
                    return filename; // Filename has to be returned in order to be able to post.
                }
            };

            // Set file
            formData.add("file", contentsAsResource);

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
        if (album.getCover().getBlob() != null) {
            EditAlbumCover(album);
        }

        album.setCover(new Cover());
        webClient.put()
                .uri("/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(album))
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

    public static byte[] GetAlbumCover(String isrc) {
        return webClient.get()
                .uri("/albums/cover/" + isrc)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    public static void EditAlbumCover(Album album) {
        try {
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

            // Convert file to ByteArrayResource
            String filename = album.getCover().getBlob().getOriginalFilename();
            ByteArrayResource contentsAsResource = new ByteArrayResource(album.getCover().getBlob().getBytes()) {
                @Override
                public String getFilename() {
                    return filename; // Filename has to be returned in order to be able to post.
                }
            };

            // Set file
            formData.add("file", contentsAsResource);

            webClient.put()
                    .uri("/albums/" + album.getIsrc())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(formData))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteAlbumCover(String isrc) {
        webClient.delete()
                .uri("/albums/cover/" + isrc)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
