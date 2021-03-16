package coreClient;

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
}
