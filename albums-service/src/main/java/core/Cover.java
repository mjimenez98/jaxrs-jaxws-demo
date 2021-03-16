package core;

import java.awt.image.BufferedImage;
import java.io.File;

public class Cover {

    private File file;
    private BufferedImage image;
    private String mimeType;

    public Cover() {
        // Could be changed to a default image
        this.image = null;
        this.mimeType = null;
    }

    public Cover(File file) {
        // basic file constructor
        this.file = file;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
