package com.vladsv.tennismatchscoreboard.model;

public enum Point {

    ZERO("0"), FIFTEEN("15"), THIRTY("30"), FORTY("40"), ADVANTAGE("AD");

    private final String value;

    Point(String value) {
        this.value = value;
    }

    public Point next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    @Override
    public String toString() {
        return value;
    }
}
