package com.vladsv.tennismatchscoreboard.model;

import com.vladsv.tennismatchscoreboard.utils.MatchState;
import com.vladsv.tennismatchscoreboard.utils.Point;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Score {
    @Getter
    @Setter
    static class PlayerScore {
        private int wonSets = 0;
        private int wonGames = 0;
        private Point currentPoint = Point.DEFAULT;

        private PlayerScore() {
        }
    }

    private Map<String, PlayerScore> playerScoreMap;

    public Score() {
        playerScoreMap = new HashMap<>();
        playerScoreMap.put("1", new PlayerScore());
        playerScoreMap.put("2", new PlayerScore());
    }

    public void updatePoint(String winnerId, MatchState matchState) {
        switch (matchState) {
            default -> {
                Point currentPoint = playerScoreMap.get(winnerId).getCurrentPoint();
                playerScoreMap.get(winnerId)
                        .setCurrentPoint(Point.values()[(currentPoint.ordinal() + 1) % (Point.values().length)]);

            }
        }
    }
}
