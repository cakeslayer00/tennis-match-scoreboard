package com.vladsv.tennismatchscoreboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OngoingMatchViewDto {

    private String firstPlayerName;
    private String secondPlayerName;
    private ScoreViewDto firstPlayerScore;
    private ScoreViewDto secondPlayerScore;

}
