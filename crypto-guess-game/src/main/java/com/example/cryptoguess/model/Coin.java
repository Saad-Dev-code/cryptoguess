package com.example.cryptoguess.model;


import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class Coin {
    private String id;        // "bitcoin"
    private String name;      // "Bitcoin"
    private String image;     // logo URL
    private double priceUsd;  // current_price
}
