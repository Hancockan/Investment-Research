package com.example.andrewhancock.investmentresearch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Hancock on 2/25/2017.
 */

public class StocksFragment extends Fragment {

    //creates a new listview called stockslist
    ListView stocksList;
    //creates a new arrayadapter for stock objects
    ArrayAdapter<Stock> adapter;
    //creates a new handler
    Handler handler;

    //number of stocks searched. This will tell from the ticker

    //string for ticker symbol
    String ticker;
    //new list of stock objects
    List<Stock> stocks;
    //new object of generate stock objects class
    GenerateStockObject stockHolder;

    //constructor
    public StocksFragment(){
        //initializes new handler
        handler = new Handler();
        //initializes new list of stock objects
        stocks = new ArrayList<Stock>();
    }

    public static Fragment newInstance(String ticker){

        //the ticker parameter passed in is the string from the edit text in the first activity

        //creates new instance of this class
        StocksFragment sf = new StocksFragment();
        //sets new instance's ticker equal to the ticker passed
        sf.ticker = ticker;
        //not totally sure what this does. new instance.object of generate stock object
        //class is a new instance of generate stock object class when passed the ticker
        sf.stockHolder = new GenerateStockObject(sf.ticker);
        //returns the fragment
        return sf;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.stocks, container, false);
        //stocks_list is the name of the list view
        stocksList = (ListView)v.findViewById(R.id.stocks_list);
        return v;
    }


    public void onCreate(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //initialize will get the number searched from the intent passed on from the starting
        //screen


        initialize();
    }

    public void initialize(){

        if(stocks.size() == 0){

            new Thread(){
                public void run(){
                    stocks.addAll(stockHolder.fetchData(ticker));

                    handler.post(new Runnable(){
                        public void run(){
                            createAdapter();
                        }
                    });
                }
            }.start();

        }
    }

    public void createAdapter(){

        if(getActivity() == null){
            return;
        }

        adapter = new ArrayAdapter<Stock>(getActivity(), R.layout.stock_item, stocks){
            public View getView(int position, View convertView, ViewGroup parent){
                if(convertView == null){
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.stock_item, null);
                }

                TextView stockTicker;
                stockTicker = (TextView)convertView.findViewById(R.id.ticker_symbol);

                TextView lastTradePrice;
                lastTradePrice = (TextView)convertView.findViewById(R.id.last_trade_price);

                TextView companyName;
                companyName = (TextView)convertView.findViewById(R.id.company_name);

                TextView dailyVolume;
                dailyVolume = (TextView)convertView.findViewById(R.id.daily_volume);

                TextView dailyHigh;
                dailyHigh = (TextView)convertView.findViewById(R.id.day_high);

                TextView dailyLow;
                dailyLow = (TextView)convertView.findViewById(R.id.day_low);

                TextView yearHigh;
                yearHigh = (TextView)convertView.findViewById(R.id.year_high);

                TextView yearLow;
                yearLow = (TextView)convertView.findViewById(R.id.year_low);

                TextView change;
                change = (TextView)convertView.findViewById(R.id.daily_change);

                stockTicker.setText(stocks.get(position).symbol);
                lastTradePrice.setText(stocks.get(position).getPrice());
                companyName.setText(stocks.get(position).companyName);
                dailyVolume.setText(stocks.get(position).getAverageDailyVolume());
                dailyHigh.setText(stocks.get(position).dayHigh());
                dailyLow.setText(stocks.get(position).dayLow());
                yearHigh.setText(stocks.get(position).yearHigh());
                yearLow.setText(stocks.get(position).yearLow());
                change.setText(stocks.get(position).change);

                return convertView;
            }
        };
        stocksList.setAdapter(adapter);

    }

}
