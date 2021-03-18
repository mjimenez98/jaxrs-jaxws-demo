package core;

import java.io.InputStream;

public class Cover {
    private InputStream blob;
    private String mimeType;

    public Cover() {
        this.blob = null;
        this.mimeType = null;
    }

    public Cover(InputStream blob) {
        this.blob = blob;
    }

    public Cover(InputStream blob, String mimeType) {
        this.blob = blob;
        this.mimeType = mimeType;
    }

    public Cover(Cover cover) {
        this.blob = cover.blob;
        this.mimeType = cover.mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public InputStream getBlob() {
        return blob;
    }

    public void setBlob(InputStream blob) {
        this.blob = blob;
    }
}
