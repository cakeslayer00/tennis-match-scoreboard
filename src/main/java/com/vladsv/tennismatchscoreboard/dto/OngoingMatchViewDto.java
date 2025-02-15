package com.vladsv.tennismatchscoreboard.dto;

import com.vladsv.tennismatchscoreboard.model.Player;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OngoingMatchViewDto {

    private Player firstPlayer;
    private Player secondPlayer;
    private ScoreViewDto firstPlayerScore;
    private ScoreViewDto secondPlayerScore;
    private boolean tieBreak;

}
