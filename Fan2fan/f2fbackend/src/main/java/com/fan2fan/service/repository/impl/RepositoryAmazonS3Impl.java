package com.fan2fan.service.repository.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.fan2fan.model.file.FileStream;
import com.fan2fan.service.repository.BasePath;
import com.fan2fan.service.repository.RepositoryService;
import com.fan2fan.util.StringUtils;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author huangsz
 */
public class RepositoryAmazonS3Impl implements RepositoryService {

    private static final char FOLDER_SUFFIX = '/';

    @Value("${S3.accessKey}")
    private String accessKey;

    @Value("${S3.accessSecretKey}")
    private String accessSecretKey;

    @Value("${S3.link}")
    private String S3Link;

    private AmazonS3Client s3Client;

    @PostConstruct
    public void init() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecretKey);
        s3Client = new AmazonS3Client(credentials);
    }

    @Override
    public void putFile(BasePath bucket, String path, String fileName, InputStream file) {
        putFile(bucket.toString(), getObjectKey(path, fileName), file);
    }

    @Override
    public List<String> getFileNames(BasePath bucket, String path) {
        List<String> fileNames = new ArrayList<>();
        ObjectListing objList = s3Client.listObjects(bucket.toString(), getS3Path(path));
        for (S3ObjectSummary summary: objList.getObjectSummaries()) {
            //ignore folders
            if(! summary.getKey().endsWith(FOLDER_SUFFIX + "")){
                fileNames.add(summary.getKey().substring(path.length()));
            }
        }
        return fileNames;
    }

    @Override
    public FileStream getFileByName(BasePath bucket, String path, String name) throws FileNotFoundException {
        S3Object obj = s3Client.getObject(new GetObjectRequest(bucket.toString(), getObjectKey(path, name)));
        return new FileStream(obj.getObjectContent(), obj.getObjectMetadata().getContentLength());
    }

    @Override
    public String getPath(BasePath base, String path, String name) {
        return String.join(File.separator, S3Link, base.toString(), path, name);
    }

    @Override
    public String createRandomFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(Files.getFileExtension(originalFileName));
    }

    private void putFile(String bucket, String key, InputStream file) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(((ByteArrayInputStream)file).available());
        AccessControlList aclList = new AccessControlList();
        aclList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        PutObjectRequest request = new PutObjectRequest(
                bucket, key, file, meta
        ).withAccessControlList(aclList);
        s3Client.putObject(request);
    }

    private String getS3Path(String path) {
        final String trimoff = StringUtils.getWhiteSpaces() + FOLDER_SUFFIX;
        return StringUtils.trimWith(path, trimoff) + FOLDER_SUFFIX;
    }

    private String getObjectKey(String path, String fileName) {
        return getS3Path(path).concat(fileName);
    }
}
