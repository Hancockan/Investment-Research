package com.example.andrewhancock.investmentresearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Hancock on 2/24/2017.
 */

/*
This class is going to create an object of the stock class with the given attributes
 */

public class GenerateStockObject {

    //url template for searching for data
    private final String URL_TEMPLATE = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.fina" +
            "nce.quote%20where%20symbol%20in%20(REPLACE)&format=json&dia" +
            "gnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

    //ticker symbol that user inputs
    String ticker_symbol;
    //final generated url
    String url;

    //constructor
    public GenerateStockObject(String TS){
        //sets user input equal to the class ticker symbol
        ticker_symbol = TS;
        //generates url with the ticker symbol in it. This can be used to get the data
        generateURL();
    }

    //generates the url that can be used to get stock data
    public void generateURL(){
        //replaces place holder with the actual ticker symbol to create the url

        System.out.println("User ticker: " + ticker_symbol);

        String[] tickerSymbols = ticker_symbol.split(",");
        String urlReplacement;
        String tmp = "";

        for(int i = 0;i < tickerSymbols.length;i++){
            String comma = ",";
            if(i == 0){
                comma = "";
            }else{
                comma = ",";
            }
            String qts = "\"";

            tmp = tmp + comma + qts + (tickerSymbols[i].toUpperCase()) + qts;
        }

        urlReplacement = tmp;
        System.out.println("The replacement string is: " +  urlReplacement);

         url = URL_TEMPLATE.replace("REPLACE", urlReplacement);

        //url = URL_TEMPLATE.replace("TICKER_SYMBOL", ticker_symbol);
    }

    //fetches the data of the stock objects in the array and set the stock objects
    //into a list
    List<Stock> fetchData(String ticker){

        //need method to find number of objects in string
        int numTickers = findNumItems(ticker);

        //fetches the raw JSON data and returns it as a string
        String raw = GetData.readContents(url);
        //declares a new list of stock objects
        List<Stock> list = new ArrayList<Stock>();

        try{
            //gets the query part of the JSON string
            JSONObject query = new JSONObject(raw).getJSONObject("query").getJSONObject("results");
            //printing out the raw JSON data
            System.out.println(query.toString());
            //gets the quote array in the query part of the JSON

            if(numTickers > 1) {
                JSONArray quote = query.getJSONArray("quote");
                //Now the stock data shows up in the JSON array but since it only needs one value,
                //it only goes through the array once
                for (int i = 0; i < quote.length(); i++) {
                    //This sets the currentstock equal to the array at the specified index
                    JSONObject currentStock = quote.getJSONObject(i);
                    //creates a new stock object
                    Stock s = new Stock();
                    //sets the year high
                    s.yearHigh = currentStock.optDouble("YearHigh");
                    //sets the year low
                    s.yearsLow = currentStock.optDouble("YearLow");
                    //set day high
                    s.daysHigh = currentStock.optDouble("DaysHigh");
                    //set day low
                    s.daysLow = currentStock.optDouble("DaysLow");
                    //sets company name
                    s.companyName = currentStock.optString("Name");
                    //sets symbol
                    s.symbol = currentStock.optString("Symbol");
                    //sets daily volume
                    s.averageDailyVolume = currentStock.optLong("AverageDailyVolume");
                    //the following sets daily change but will be handeled later because of the signs
                    s.change = currentStock.optString("Change");
                    //sets price
                    s.lastTradePrice = currentStock.optDouble("LastTradePriceOnly");

                    //this checks to see if the stock is valid and if it is it will add it to the list
                    if (s.symbol != null) {
                        list.add(s);
                    }

                }
            }else{
                JSONObject quoteNonArray = query.getJSONObject("quote");
                //creates a new stock object
                Stock s = new Stock();
                //sets the year high
                s.yearHigh = quoteNonArray.optDouble("YearHigh");
                //sets the year low
                s.yearsLow = quoteNonArray.optDouble("YearLow");
                //set day high
                s.daysHigh = quoteNonArray.optDouble("DaysHigh");
                //set day low
                s.daysLow = quoteNonArray.optDouble("DaysLow");
                //sets company name
                s.companyName = quoteNonArray.optString("Name");
                //sets symbol
                s.symbol = quoteNonArray.optString("Symbol");
                //sets daily volume
                s.averageDailyVolume = quoteNonArray.optLong("AverageDailyVolume");
                //the following sets daily change but will be handeled later because of the signs
                s.change = quoteNonArray.optString("Change");
                //sets price
                s.lastTradePrice = quoteNonArray.optDouble("LastTradePriceOnly");

                //this checks to see if the stock is valid and if it is it will add it to the list
                if (s.symbol != null) {
                    list.add(s);
                }

            }

        }catch (Exception e){
            Log.e("fetchData()", e.toString());
        }
        return list;
    }

    //returns the number of search terms in the query in order to tell whether or not
    //quotes will be an array
    public int findNumItems(String ticker){
        if(ticker.contains(",") == false){
            System.out.println("The amount of tickers: 1");
            return 1;
        }else{
            String [] tickers = ticker.split(",");
            System.out.println("The amount of tickers: " + tickers.length);
            return tickers.length;
        }

    }

}
