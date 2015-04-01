package com.fan2fan.service.repository;

import com.fan2fan.model.file.FileStream;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Service for saving, retrieving and listring files
 *
 * @author huangsz
 */
public interface RepositoryService {

    /**
     * put a file to repository. If use Cloud, default permission is PUBLIC-READ
     * @param base: the base folder of the file
     * @param path
     * @param fileName
     * @param file
     */
    public void putFile(BasePath base, String path, String fileName, InputStream file);

    /**
     * get file name list from a path
     * @param base
     * @param path
     * @return
     */
    public List<String> getFileNames(BasePath base, String path);

    /**
     * get file content
     * @param base
     * @param path
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    public FileStream getFileByName(BasePath base, String path, String name) throws FileNotFoundException;

    /**
     * get url of file
     * @param base : the base folder of the file
     * @param path : the path of the file
     * @param name : the name of the file
     * @return the completed path of the file.
     */
    public String getPath(BasePath base, String path, String name);

    /**
     * create a random file name, based on the extension of the orignial
     * @param originalFileName
     * @return
     */
    public String createRandomFileName(String originalFileName);

}
