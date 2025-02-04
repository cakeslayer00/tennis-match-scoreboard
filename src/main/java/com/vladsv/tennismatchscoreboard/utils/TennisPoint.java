package com.vladsv.tennismatchscoreboard.utils;

import lombok.Getter;

@Getter
public enum TennisPoint {

    FIRST_POINT("0") , SECOND_POINT("15"), THIRD_POINT("30"), FOURTH_POINT("40"), ADVANCE_POINT("AD");

    private final String value;

    public TennisPoint next() {
        return values()[(ordinal() + 1) % values().length];
    }

    TennisPoint(String value) {
        this.value = value;
    }

}
