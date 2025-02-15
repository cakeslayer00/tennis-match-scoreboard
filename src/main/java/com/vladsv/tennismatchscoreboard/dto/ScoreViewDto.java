package com.vladsv.tennismatchscoreboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreViewDto {

    private int sets;
    private int games;
    private String point;
    private int tieBreakCounter;

}
