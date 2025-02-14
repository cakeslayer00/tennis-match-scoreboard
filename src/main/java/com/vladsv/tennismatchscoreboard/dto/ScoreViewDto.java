package com.vladsv.tennismatchscoreboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreViewDto {

    private String point;
    private int games;
    private int sets;

}
