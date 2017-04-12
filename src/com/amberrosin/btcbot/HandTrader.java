package com.amberrosin.btcbot;

import com.assist.TradeApi;

import java.util.Scanner;

/**
 * Created by str on 31.03.2017.
 */

public class HandTrader {
    private Long reuseMillis = new Long(30000);
    private String currentPair = new String();
    private String currentAction = new String();
    private String[] currentPairDivided = new String[2];
    private String apiKey = new String();
    private String apiSecret = new String();
    private boolean exit = false;

    HandTrader(String apikey, String apisecret) {
        apiKey = apikey;
        apiSecret = apisecret;
        System.out.printf("Enter pairs you want bot to trade. Example: btc-usd\n$>");
        Scanner getPairs = new Scanner(System.in);
        currentPair = getPairs.nextLine();
        currentPairDivided = currentPair.split("-");
        System.out.printf("\n");
    }

    public void simpleTrade() throws Exception {
        Scanner getDecision = new Scanner(System.in);
        TradeApi forSimpleTrade = new TradeApi(apiKey, apiSecret);
        forSimpleTrade.ticker.addPair(currentPair);
        forSimpleTrade.ticker.runMethod();
        forSimpleTrade.getInfo.runMethod();

        while (!exit) {
            System.out.printf("What do you want to do ?\n\n1 - Currency full sell for current market price\n2 - Currency full buy for current market price\n" +
                    "3 - Get first currency balance\n4 - Get second currency balance\n5 - Get current pair market price\n6 - Get current pair market sell price\n" +
                    "7 - Get current pair market buy price\n8 - Back to main menu\n$>");
            Scanner getCurrentAction = new Scanner(System.in);
            while(true) {
                currentAction = getCurrentAction.nextLine();
                if(currentAction.equals("1") || currentAction.equals("2") || currentAction.equals("3") || currentAction.equals("4") || currentAction.equals("5") || currentAction.equals("6")
                        || currentAction.equals("7")) break;
                else if(currentAction.equals("8")) {
                    exit = true;
                    break;
                }
                else System.out.printf("Incorrect input, please try again\n$>");
            }
            if(currentAction.equals("1")) {
                System.out.printf("You`re going to sell all your second currency, proceed ? Y/N\n$>");
                if(getDecision.equals("Y")) {
                    forSimpleTrade.tryMaximumSell(currentPair);
                }
                else System.out.printf("Sell cancelled\n");
            }
            if(currentAction.equals("2")) {
                System.out.printf("You`re going to full buy currency, proceed ? Y/N\n$>");
                if(getDecision.equals("Y")) {
                    forSimpleTrade.tryMaximumBuy(currentPair, reuseMillis);
                }
            }
            if(currentAction.equals("3")) {
                System.out.printf("Your first currency active balance is %s %S\n\n", forSimpleTrade.getInfo.getBalance(currentPairDivided[0]), currentPairDivided[0]);
            }
            if(currentAction.equals("4")) {
                System.out.printf("Your second currency active balance is %s %S\n\n", forSimpleTrade.getInfo.getBalance(currentPairDivided[1]), currentPairDivided[1]);
            }
            if(currentAction.equals("5")) {
                forSimpleTrade.ticker.switchNextPair();
                System.out.printf("Current pair market price is %s %S\n\n", forSimpleTrade.ticker.getCurrentLast(), currentPairDivided[1]);
                forSimpleTrade.ticker.switchResetPair();
            }
            if(currentAction.equals("6")) {
                forSimpleTrade.ticker.switchNextPair();
                System.out.printf("Current pair market sell price is %s %S\n\n", forSimpleTrade.ticker.getCurrentSell(), currentPairDivided[1]);
                forSimpleTrade.ticker.switchResetPair();
            }
            if(currentAction.equals("7")) {
                forSimpleTrade.ticker.switchNextPair();
                System.out.printf("Current pair market buy price is %s %S\n\n", forSimpleTrade.ticker.getCurrentBuy(), currentPairDivided[1]);
                forSimpleTrade.ticker.switchResetPair();
            }
        }
    }
}
