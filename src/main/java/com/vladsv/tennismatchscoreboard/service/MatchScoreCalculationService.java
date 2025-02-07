package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.model.Score;
import com.vladsv.tennismatchscoreboard.utils.MatchState;

public class MatchScoreCalculationService {

    public void updateScore(OngoingMatch ongoingMatch, String winnerId) {
        Score score = ongoingMatch.getScore();
        MatchState matchState = ongoingMatch.getMatchState();

        score.updatePoint(winnerId, matchState);
    }

}
