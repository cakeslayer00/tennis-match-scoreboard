package com.vladsv.tennismatchscoreboard.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OngoingMatch {

    private Long firstPlayerId;
    private Long secondPlayerId;
    private Long winnerId;
    private MatchState matchState;
    private Score score;

}
