package com.vladsv.tennismatchscoreboard.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OngoingMatch {

    private Player firstPlayer;
    private Player secondPlayer;
    private Player winnerPlayer;
    @Builder.Default
    private MatchState matchState = MatchState.DEFAULT;
    @Builder.Default
    private Score firstPlayerScore = new Score();
    @Builder.Default
    private Score secondPlayerScore = new Score();

    public static final int SETS_TO_WIN = 2;
    public static final int GAMES_TO_TIE_BREAK = 6;

    public boolean isMatchFinished() {
        return firstPlayerScore.getSets() >= SETS_TO_WIN
                || secondPlayerScore.getSets() >= SETS_TO_WIN;
    }

    public boolean isTieBreak() {
        return firstPlayerScore.getGames() == secondPlayerScore.getGames()
                && firstPlayerScore.getGames() >= GAMES_TO_TIE_BREAK;
    }

    public boolean isDeuce() {
        return firstPlayerScore.getPoint().ordinal() >= Point.FORTY.ordinal()
                && secondPlayerScore.getPoint().ordinal() >= Point.FORTY.ordinal();
    }

}
