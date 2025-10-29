package com.example.cryptoguess.model;


import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class GameSession {
    private String username;
    private int score;            // total correct
    private int rounds;           // rounds played
    private Set<Badge> badges = new LinkedHashSet<>();
    private GameRound currentRound;
    public void reset() {
        score = 0; rounds = 0; badges.clear(); currentRound = null;
    }
}
