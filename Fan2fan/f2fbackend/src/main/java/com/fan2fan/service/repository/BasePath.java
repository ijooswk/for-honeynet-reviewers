package com.fan2fan.service.repository;

/**
 * The basic paths of the repository.
 * such as the buckets for S3 implementation
 *
 * @author huangsz
 */
public enum BasePath {
    SYSTEMS("f2fsystems"), TEAMS("f2fteams"), USERS("f2fusers"),
    TEMPORARY("f2ftemp");

    private String path;

    private BasePath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}
