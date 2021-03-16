package client;

import coreClient.Album;
import coreClient.Artist;
import coreClient.Cover;
import coreClient.Search;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rest.RestCall;

import java.util.List;

@Controller
public class AlbumsController {

//    INDEX

    @GetMapping("/albums")
    public String getAlbums(@ModelAttribute Search search, Model model) {
        List<Album> albums = RestCall.GetAlbums();
        model.addAttribute("albums", albums);
        model.addAttribute("search", new Search());

        return "albums";
    }

//    SHOW

    @GetMapping("/albums/{isrc}")
    public String getAlbum(@PathVariable("isrc") String isrc, Model model) {
        Album album = RestCall.GetAlbum(isrc);
        model.addAttribute("album", album);

        return "show";
    }

//    NEW

    @GetMapping("/albums/new")
    public String newAlbum(Model model) {
        model.addAttribute("album", new Album());

        return "new";
    }

    @PostMapping("/albums/new")
    public String newAlbumSubmit(@ModelAttribute Album album, Model model) {
        album.setCover(new Cover(null, null));
        RestCall.CreateAlbum(album);

        return "redirect:/albums";
    }

//    EDIT

    @GetMapping("/albums/edit/{isrc}")
    public String editAlbum(@PathVariable("isrc") String isrc, Model model) {
        Album album = RestCall.GetAlbum(isrc);
        model.addAttribute("album", album);

        return "edit";
    }

    @PostMapping("/albums/edit/{isrc}")
    public String editAlbumSubmit(@ModelAttribute Album album, Model model) {
        album.setCover(new Cover(null, null));
        RestCall.EditAlbum(album);

        return "redirect:/albums";
    }

//    DELETE

    @GetMapping("/albums/delete/{isrc}")
    public String deleteAlbum(@PathVariable("isrc") String isrc, Model model) {
        RestCall.DeleteAlbum(isrc);

        return "redirect:/albums";
    }
}
