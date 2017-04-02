package com.example.andrewhancock.investmentresearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String userQuery = intent.getExtras().getString("query");
        System.out.println(userQuery);

        addFragment(userQuery);
    }

    void addFragment(String userQuery){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_holder, StocksFragment.newInstance(userQuery)).commit();
    }
}
