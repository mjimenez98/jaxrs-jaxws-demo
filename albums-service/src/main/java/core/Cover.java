package core;

import java.awt.image.BufferedImage;

public class Cover {
    private BufferedImage image;
    private String mimeType;

    public Cover() {
        // Could be changed to a default image
        this.image = null;
        this.mimeType = null;
    }

    public Cover(BufferedImage image, String mimeType) {
        this.image = image;
        this.mimeType = mimeType;
    }

    public Cover(Cover cover) {
        this.image = cover.image;
        this.mimeType = cover.mimeType;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
