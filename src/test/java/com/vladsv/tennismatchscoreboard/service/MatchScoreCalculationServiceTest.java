package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.model.Player;
import com.vladsv.tennismatchscoreboard.model.score.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchScoreCalculationServiceTest {

    @Test
    void givenFirstPlayerWonScore_whenOnDeuce_thenGamesCounterShouldNotChange() {
        OngoingMatch match = OngoingMatch.builder()
                .firstPlayer(new Player(1L, "Dummy"))
                .secondPlayer(new Player(2L, "Dummy2"))
                .build();

        MatchScoreCalculationService service = new MatchScoreCalculationService();

        match.getFirstPlayerScore().setPoint(Point.FORTY);
        match.getSecondPlayerScore().setPoint(Point.FORTY);

        service.updateMatchScore(match, "1");

        assertEquals(0, match.getFirstPlayerScore().getGames());
    }

    @Test
    void givenFirstPlayerWonScore_whenDifferenceInScoreMoreThanTwo_thenFirstPlayerWonGame() {
        OngoingMatch match = OngoingMatch.builder()
                .firstPlayer(new Player(1L, "Dummy"))
                .secondPlayer(new Player(2L, "Dummy2"))
                .build();

        MatchScoreCalculationService service = new MatchScoreCalculationService();

        match.getFirstPlayerScore().setPoint(Point.FORTY);

        service.updateMatchScore(match, "1");

        assertEquals(1, match.getFirstPlayerScore().getGames());
    }

    @Test
    void givenBothPlayers_whenBothHaveSixWonSets_thenTieBreakShouldStart() {
        OngoingMatch match = OngoingMatch.builder()
                .firstPlayer(new Player(1L, "Dummy"))
                .secondPlayer(new Player(2L, "Dummy2"))
                .build();

        MatchScoreCalculationService service = new MatchScoreCalculationService();

        match.getFirstPlayerScore().setGames(6);
        match.getSecondPlayerScore().setGames(6);

        service.updateMatchScore(match, "1");

        assertTrue(match.isTieBreak());
    }

}
