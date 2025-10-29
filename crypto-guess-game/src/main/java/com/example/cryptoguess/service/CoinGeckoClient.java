package com.example.cryptoguess.service;

import com.example.cryptoguess.model.Coin;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CoinGeckoClient {
    private final RestTemplate http = new RestTemplate();

    private static final String URL =
        "https://api.coingecko.com/api/v3/coins/markets" +
        "?vs_currency=usd&order=market_cap_desc&per_page=50&page=1&sparkline=false";

    // --- simple in-memory cache/throttle ---
    private volatile List<Coin> cached = null;
    private volatile long cacheAtMs = 0L;
    private static final long CACHE_TTL_MS = Duration.ofSeconds(60).toMillis(); // 60s
    private static final long MIN_INTERVAL_MS = 1500; // 1.5s between calls
    private final AtomicLong lastCall = new AtomicLong(0);

    public List<Coin> fetchTop50() {
        long now = System.currentTimeMillis();

        // serve fresh cache if valid
        if (cached != null && (now - cacheAtMs) < CACHE_TTL_MS) {
            return cached;
        }

        // simple throttle
        long last = lastCall.get();
        if (now - last < MIN_INTERVAL_MS) {
            // too soon: serve cache if any, else fallback demo
            return cached != null ? cached : DemoData.coins();
        }
        lastCall.set(now);

        try {
            ResponseEntity<List> resp = http.getForEntity(URL, List.class);
            List<Map<String, Object>> body = resp.getBody();
            List<Coin> coins = new ArrayList<>();
            if (body != null) {
                for (Map<String, Object> m : body) {
                    coins.add(new Coin(
                        (String) m.get("id"),
                        (String) m.get("name"),
                        (String) m.get("image"),
                        ((Number) m.get("current_price")).doubleValue()
                    ));
                }
            }
            if (!coins.isEmpty()) {
                cached = coins;
                cacheAtMs = System.currentTimeMillis();
                return coins;
            }
            // empty? fall back
            return cached != null ? cached : DemoData.coins();

        } catch (HttpClientErrorException.TooManyRequests e) {
            // rate limited → use cache or offline list
            return cached != null ? cached : DemoData.coins();
        } catch (Exception e) {
            // any other upstream error → degrade gracefully
            return cached != null ? cached : DemoData.coins();
        }
    }
}
