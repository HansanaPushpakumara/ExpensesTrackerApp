package com.example.expensesapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class QuoteActivity extends Activity {

    public static TextView quoteTxt;
    public static TextView authorTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        //-----Api handling
        quoteTxt = (TextView) findViewById(R.id.quoteTxt);
        authorTxt = (TextView) findViewById(R.id.authorTxt);

        ApiHandler process = new ApiHandler();
        process.execute();

    }
}