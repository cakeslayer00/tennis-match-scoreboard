package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.model.MatchState;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.model.Point;
import com.vladsv.tennismatchscoreboard.model.Score;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static com.vladsv.tennismatchscoreboard.model.OngoingMatch.GAMES_TO_TIE_BREAK;

@RequiredArgsConstructor
public class MatchScoreCalculationService {

    public void updateState(OngoingMatch ongoingMatch) {
        if (ongoingMatch.isDeuce()) {
            ongoingMatch.setMatchState(MatchState.DEUCE);
        } else if (ongoingMatch.isTieBreak()) {
            ongoingMatch.setMatchState(MatchState.TIE_BREAK);
        } else {
            ongoingMatch.setMatchState(MatchState.DEFAULT);
        }
    }

    public void updateScore(OngoingMatch ongoingMatch, String winnerId) {
        Score winnerScore = getWinnerScore(ongoingMatch, winnerId);
        Score loserScore = getLoserScore(ongoingMatch, winnerId);

        switch (ongoingMatch.getMatchState()) {
            case DEFAULT -> processOngoing(winnerScore, loserScore);
            case DEUCE -> onDeuce(winnerScore, loserScore);
            case TIE_BREAK -> {
                onTieBreak(winnerScore, loserScore);
                return;
            }
        }

        if (hasWonSet(winnerScore,loserScore)) {
            winnerScore.incrementSets();
            resetPoints(winnerScore,loserScore);
            resetGames(winnerScore,loserScore);
        }
    }

    private void processOngoing(Score winnerScore, Score loserScore) {
        if (winnerScore.getPoint().equals(Point.FORTY)
                && !loserScore.getPoint().equals(Point.FORTY)) {
            winnerScore.incrementGames();
            resetPoints(winnerScore, loserScore);
        } else {
            winnerScore.incrementPoint();
        }
    }

    private void onDeuce(Score winnerScore, Score loserScore) {
        if (loserScore.getPoint().equals(Point.ADVANTAGE)) {
            resetToDeuce(winnerScore, loserScore);
        } else {
            if (winnerScore.getPoint().equals(Point.ADVANTAGE)) {
                winnerScore.incrementGames();
                resetPoints(winnerScore,loserScore);
            } else {
                winnerScore.incrementPoint();
            }
        }
    }

    private void onTieBreak(Score winnerScore, Score loserScore) {
        winnerScore.incrementTieBreakCounter();

        if (hasWonTieBreak(winnerScore, loserScore)) {
            winnerScore.incrementSets();
            resetPoints(winnerScore, loserScore);
            resetGames(winnerScore, loserScore);
            resetTieBreakCounter(winnerScore,loserScore);
        }
    }

    private void resetGames(Score winnerScore, Score loserScore) {
        winnerScore.setGames(0);
        loserScore.setGames(0);
    }

    private void resetPoints(Score winnerScore, Score loserScore) {
        winnerScore.setPoint(Point.ZERO);
        loserScore.setPoint(Point.ZERO);
    }

    private void resetToDeuce(Score winnerScore, Score loserScore) {
        winnerScore.setPoint(Point.FORTY);
        loserScore.setPoint(Point.FORTY);
    }

    private void resetTieBreakCounter(Score winnerScore, Score loserScore) {
        winnerScore.setTieBreakCounter(0);
        loserScore.setTieBreakCounter(0);
    }

    private boolean hasWonSet(Score winnerScore, Score loserScore) {
        int diff = Math.abs(winnerScore.getGames() - loserScore.getGames());
        return diff >= 2 && winnerScore.getGames() >= GAMES_TO_TIE_BREAK;
    }

    private boolean hasWonTieBreak(Score winnerScore, Score loserScore) {
        int diff = winnerScore.getTieBreakCounter() - loserScore.getTieBreakCounter();

        return diff >= 2 && winnerScore.getTieBreakCounter() >= 7;
    }

    private Score getWinnerScore(OngoingMatch ongoingMatch, String winnerId) {
        return Objects.equals(ongoingMatch.getFirstPlayer().getId(), Long.valueOf(winnerId))
                ? ongoingMatch.getFirstPlayerScore()
                : ongoingMatch.getSecondPlayerScore();
    }

    private Score getLoserScore(OngoingMatch ongoingMatch, String winnerId) {
        return Objects.equals(ongoingMatch.getFirstPlayer().getId(), Long.valueOf(winnerId))
                ? ongoingMatch.getSecondPlayerScore()
                : ongoingMatch.getFirstPlayerScore();
    }

}
