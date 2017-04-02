package com.example.andrewhancock.investmentresearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class startPage extends AppCompatActivity {

    EditText searchbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        //declares a search box obejct so i can get the text out of the box
        searchbox = (EditText)findViewById(R.id.search_box);

        //declares intent to go to the listview displaying info
        final Intent goToStockInfo = new Intent(this, MainActivity.class);

        //declares search button so that an on click method can be added
        final Button search = (Button)findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String query = getDaText();

                System.out.println("The query is: " + query);

                goToStockInfo.putExtra("query", query);
                startActivity(goToStockInfo);
            }
        });

    }

    public String getDaText(){
        String query = searchbox.getText().toString();
        return query;
    }
}
