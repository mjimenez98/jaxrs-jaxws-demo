//International Standard Recording Code (ISRC): unique
//Title
//Content description: optional
//Release year
//Artist's first and last names^
//Cover image: optional^^

public class AlbumGateway{
    public Record find(int isrc){
        // find album by ISRC
    }
    public Record findByAlbumTitle(){
        // find album by title
    }
    public void update(int isrc, String title, String description, int releaseYear, Artist artist, Cover cover){
        // Update album.
    }
    public void insert(int isrc, String title, String description, int releaseYear, Artist artist, Cover cover){
        // insert album.
    }
    public void delete(int isrc){
        // Delete album by ISRC.
    }
}