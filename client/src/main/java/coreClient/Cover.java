package coreClient;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

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

    // DOES NOT WORK
    public Resource getBlobAsResource() throws IOException {
        InputStream initialStream = blob.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File("src/main/resources/" + blob.getOriginalFilename());

        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            outStream.write(buffer);
        }

        return new FileSystemResource(targetFile);
    }

    public void setBlob(MultipartFile blob) {
        this.blob = blob;
    }
}
