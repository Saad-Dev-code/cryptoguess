package com.example.cryptoguess.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameRound {
    private Coin coinA;
    private Coin coinB;
    private String correct; // "A" or "B"
    private boolean answered;
    private boolean correctGuess;
}
