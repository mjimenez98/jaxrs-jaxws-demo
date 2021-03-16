package core;

import java.awt.image.BufferedImage;
import java.io.File;

public class Cover {

    private File file;
    private String mimeType;

    public Cover() {
        // Could be changed to a default image
        this.file = null;
        this.mimeType = null;
    }

    public Cover(File file) {
        // basic file constructor
        this.file = file;
    }

    public Cover(File file, String mimeType) {
        this.file = file;;
        this.mimeType = mimeType;
    }

    public Cover(Cover cover) {
        this.file = cover.file;
        this.mimeType = cover.mimeType;
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
