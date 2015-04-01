package com.fan2fan.service.repository.impl;

import com.fan2fan.model.file.FileStream;
import com.fan2fan.service.repository.BasePath;
import com.fan2fan.service.repository.RepositoryService;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author huangsz
 */
@Service
public class RepositoryFileSystemImpl implements RepositoryService {

    public static final Logger logger = LoggerFactory.getLogger(RepositoryFileSystemImpl.class);

    @Value("${images.storage.location}")
    private String repositoryPath;

    private String getAbsolutePath(BasePath basePath, String path, String fileName) {
        return String.join(File.separator, repositoryPath, basePath.toString(), path, fileName);
    }

    private void createNewDir(BasePath basePath, String path) {
        File dir = new File(String.join(File.separator, repositoryPath, basePath.toString(), path));
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void putFile(BasePath basePath, String path, String fileName, InputStream file) {
        createNewDir(basePath, path);
        File fd = new File(getAbsolutePath(basePath, path, fileName));
        try {
            Files.write(ByteStreams.toByteArray(file), fd);
        } catch (IOException e) {
            logger.error("put file error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileStream getFileByName(BasePath basePath, String path, String fileName) throws FileNotFoundException {

        return null;
    }

    @Override
    public String getPath(BasePath base, String path, String name) {
        return String.join(File.separator, "", base.toString(), path, name);
    }

    @Override
    public List<String> getFileNames(BasePath basePath, String path) {

        return null;
    }

    @Override
    public String createRandomFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(Files.getFileExtension(originalFileName));
    }

}
