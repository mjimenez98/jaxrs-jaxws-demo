package coreClient;

import org.springframework.web.multipart.MultipartFile;

public class Cover {
    private MultipartFile blob;
    private String mimeType;

    public Cover() {
        this.blob = null;
        this.mimeType = null;
    }

    public Cover(MultipartFile blob) {
        this.blob = blob;
    }

    public Cover(MultipartFile blob, String mimeType) {
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

    public MultipartFile getBlob() {
        return blob;
    }

    public void setBlob(MultipartFile blob) {
        this.blob = blob;
    }
}
