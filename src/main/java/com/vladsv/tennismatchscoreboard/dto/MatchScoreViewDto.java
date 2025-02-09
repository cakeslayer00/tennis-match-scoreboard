package com.vladsv.tennismatchscoreboard.dto;

import com.vladsv.tennismatchscoreboard.model.Score;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchScoreViewDto {

    private String firstPlayerName;
    private String secondPlayerName;
    private Score.PlayerScore firstPlayerScore;
    private Score.PlayerScore secondPlayerScore;
    private boolean isTieBreak;

    public boolean isTieBreak() {
        return isTieBreak;
    }

}
