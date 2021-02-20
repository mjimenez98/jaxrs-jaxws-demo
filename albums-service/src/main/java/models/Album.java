package models;

public class Album implements AlbumInt {
    private String description;
    private String isrc;
    private String title;
    private int releaseYear;

    private Artist artist;

    public Album() {
        this.description = null;
        this.isrc = null;
        this.title = null;
        this.releaseYear = -1;
        this.artist = new Artist();
    }

    public Album(String artistName, String description, String isrc, String title, int releaseYear, Artist artist) {
        this.description = description;
        this.isrc = isrc;
        this.title = title;
        this.releaseYear = releaseYear;
        this.artist = new Artist(artist);
    }

    public Album(Album album) {
        this.description = album.description;
        this.isrc = album.isrc;
        this.title = album.title;
        this.releaseYear = album.releaseYear;
        this.artist = new Artist(album.artist);
    }

    public String getDescription() {
        return description;
    }

    public String getIsrc() {
        return isrc;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(Artist artist) {
        this.artist = new Artist(artist);
    }
}
