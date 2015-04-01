package com.fan2fan.model.file;

import java.io.InputStream;

/**
 * @author huangsz
 */
public class FileStream {

    private InputStream inputStream;

    private long size;

    public FileStream(InputStream input, long size) {
        this.inputStream = input;
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
