package com.example.cryptoguess.model;


import lombok.Getter;

@Getter
public enum Badge {
    ROOKIE(3, "Rookie 🎯"),
    APPRENTICE(5, "Apprentice 🛡️"),
    PRO(10, "Pro 🏆"),
    LEGEND(20, "Legend 👑");

    private final int threshold;
    private final String label;

    Badge(int threshold, String label) {
        this.threshold = threshold;
        this.label = label;
    }
}
