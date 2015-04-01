package com.fan2fan.model;

/**
 * Model Basic Rules:
 * 1. Use Integer, Boolean, Long instead of int, boolean, long
 * 2. If you're going to use enum, then make an Integer to represent it, but use enum_type in getter and setter
 * 3. Had better not to set any default values here
 *
 * The base class of all database models
 * @author huangsz
 */
public abstract class BaseModel {

    private Long id;

    /**
     * force that there should be a default constructor without parameters
     */
    public BaseModel() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
