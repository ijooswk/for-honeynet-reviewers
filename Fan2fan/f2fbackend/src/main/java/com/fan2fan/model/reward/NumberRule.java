package com.fan2fan.model.reward;

/**
 * Model Basic Rules:
 * 1. Use Integer, Boolean, Long instead of int, boolean, long
 * 2. If you're going to use enum, then make an Integer to represent it, but use enum_type in getter and setter
 * 3. Had better not to set any default values here
 *
 * User meets the trigger and the number expected, then he can get the points
 *
 * @author huangsz
 */
public class NumberRule extends SimpleRule {

    /**
     * if the trigger
     */
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
