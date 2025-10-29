package com.example.cryptoguess.service;


import com.example.cryptoguess.model.Coin;

import java.util.List;

final class DemoData {
    private DemoData() {}
    static List<Coin> coins() {
        return List.of(
            new Coin("bitcoin","Bitcoin","https://assets.coingecko.com/coins/images/1/large/bitcoin.png", 68000),
            new Coin("ethereum","Ethereum","https://assets.coingecko.com/coins/images/279/large/ethereum.png", 3500),
            new Coin("tether","Tether","https://assets.coingecko.com/coins/images/325/large/Tether-logo.png", 1.00),
            new Coin("binancecoin","BNB","https://assets.coingecko.com/coins/images/825/large/binance-coin-logo.png", 560),
            new Coin("solana","Solana","https://assets.coingecko.com/coins/images/4128/large/solana.png", 170),
            new Coin("ripple","XRP","https://assets.coingecko.com/coins/images/44/large/xrp-symbol-white-128.png", 0.55),
            new Coin("cardano","Cardano","https://assets.coingecko.com/coins/images/975/large/cardano.png", 0.40),
            new Coin("dogecoin","Dogecoin","https://assets.coingecko.com/coins/images/5/large/dogecoin.png", 0.12)
        );
    }
}
