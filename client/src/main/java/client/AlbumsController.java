package client;

import coreClient.Album;
import coreClient.Artist;
import coreClient.Cover;
import coreClient.Search;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AlbumsController {

//    ALL ALBUMS

    @GetMapping("/albums")
    public String getAlbums(@ModelAttribute Search search, Model model) {
        model.addAttribute("search", new Search());

        List<Album> albums = new ArrayList<>();
        albums.add(new Album("1", "2", "3", 4,
                new Artist("A", "B"),
                new Cover(null, null)));
        albums.add(new Album("5", "6", "7", 8,
                new Artist("A", "B"),
                new Cover(null, null)));

        model.addAttribute("albums", albums);
        return "albums";
    }

//    NEW ALBUM

    @GetMapping("/albums/new")
    public String newAlbum(Model model) {
        model.addAttribute("album", new Album());
        return "new";
    }

    @PostMapping("/albums/new")
    public String newAlbumSubmit(@ModelAttribute Album album, Model model) {
        return "redirect:/albums";
    }

//    EDIT ALBUM

    @GetMapping("/albums/edit/{isrc}")
    public String editAlbum(@PathVariable("isrc") String isrc, Model model) {
        model.addAttribute("album", new Album("1", "2", "3", 4,
                        new Artist("A", "B"),
                        new Cover(null, null)));
        model.addAttribute("isrc", isrc);

        return "edit";
    }

    @PostMapping("/albums/edit/{isrc}")
    public String editAlbumSubmit(@ModelAttribute Album album, Model model) {
        return "redirect:/albums";
    }
}
