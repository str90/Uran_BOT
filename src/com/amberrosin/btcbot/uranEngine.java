package com.amberrosin.btcbot;

public class uranEngine {

    public static void main(String[] args) throws Exception {
        User_db db = new User_db();
        db.loadConfigFile();
        System.out.printf("$$$Uran BOT v0.0$$$(Work in progress)\n\nLoaded API key: %S\nLoaded API secret: %s\n\n", db.getApiKey(), db.getApiSecret());
        HandTrader handTrader = new HandTrader(db.getApiKey(), db.getApiSecret());
        handTrader.simpleTrade();
    }
}