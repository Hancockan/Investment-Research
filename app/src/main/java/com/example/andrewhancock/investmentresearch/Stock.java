package com.example.andrewhancock.investmentresearch;

import java.text.DecimalFormat;

/**
 * Created by Andrew Hancock on 2/24/2017.
 */

/*
This class is called each time a new stock name is queried because a new
stock object is created for the corresponding ticker symbol
 */
public class Stock {

    //instead of a constructor setting the values the parser does it when
    //it reads the JSON data

    //ticker symbol
    String symbol;
    //average volume traded daily
    Long averageDailyVolume;
    //company name
    String companyName;
    //day's change
    String change;
    //day's low
    double daysLow;
    //day's high
    double daysHigh;
    //Year low
    double yearsLow;
    //Year high
    double yearHigh;
    //actual price
    double lastTradePrice;

    String getPrice() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        String lastTradePriceString = "$" + df.format(lastTradePrice);
        return lastTradePriceString;
    }

    String getAverageDailyVolume(){
        DecimalFormat df = new DecimalFormat("#,###");
        String volume = df.format(averageDailyVolume);
        return volume;
    }

    String dayHigh(){
        DecimalFormat df = new DecimalFormat("#,###.00");
        String dh = "$" + (df.format(daysHigh));
        return dh;
    }

    String yearHigh(){
        DecimalFormat df = new DecimalFormat("#,###.00");
        String dh = "$" + (df.format(yearHigh));
        return dh;
    }

    String dayLow(){
        DecimalFormat df = new DecimalFormat("#,###.00");
        String dh = "$" + (df.format(daysLow));
        return dh;
    }

    String yearLow(){
        DecimalFormat df = new DecimalFormat("#,###.00");
        String dh = "$" + (df.format(yearsLow));
        return dh;
    }

}
