package com.vladsv.tennismatchscoreboard.utils;

public class Validator {

    public String getValidatedWinnerId(String id) {
        if (!id.matches("[0-9]")) {
            throw new IllegalArgumentException("Invalid winner id");
        }
        return id;
    }

}
