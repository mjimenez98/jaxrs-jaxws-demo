package coreClient;

import javax.ws.rs.core.MediaType;
import java.io.Serializable;

public class Cover implements Serializable {

    private String image;
    private MediaType mimeType;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MediaType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MediaType mimeType) {
        this.mimeType = mimeType;
    }

    public Cover() {
        // Could be changed to a default image
        this.image = null;
        this.mimeType = null;
    }

    public Cover(String image, MediaType mimeType) {
        this.image = image;
        this.mimeType = mimeType;
    }

    public Cover(Cover cover) {
        this.image = cover.image;
        this.mimeType = cover.mimeType;
    }
}
