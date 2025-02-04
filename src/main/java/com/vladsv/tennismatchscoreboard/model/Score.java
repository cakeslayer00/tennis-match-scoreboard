package com.vladsv.tennismatchscoreboard.model;

import com.vladsv.tennismatchscoreboard.utils.TennisPoint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
public class Score {

     private final Map<String, TennisPoint> points;
     private Integer game;


}
