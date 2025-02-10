package com.vladsv.tennismatchscoreboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreViewDto {

    private int points;
    private int games;
    private int sets;

}
