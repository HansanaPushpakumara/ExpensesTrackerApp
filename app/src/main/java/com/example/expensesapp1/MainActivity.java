package com.example.expensesapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String ListID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addbtn = (Button) findViewById(R.id.addBtn);
        /*FloatingActionButton addBtn = (FloatingActionButton) findViewById(R.id.addCatBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddNewActivity.class);
                startActivity(intent);
            }
        });*/


        //-------------fetch data----------//
        DbHandler db = new DbHandler(this);

        final ArrayList<HashMap<String, String>> expensesList = db.GetExpenses();

        //-------if expenses are empty set those behaviors---//

        LinearLayout middle = (LinearLayout)findViewById(R.id.middle);
        TextView emtyTxt = (TextView) findViewById(R.id.emptyTxts);

        if(expensesList.size()==0){
            emtyTxt.setVisibility(View.VISIBLE);
            addbtn.setVisibility(View.VISIBLE);
            middle.setVisibility(View.INVISIBLE);
        }
        else{
            emtyTxt.setVisibility(View.INVISIBLE);
            addbtn.setVisibility(View.INVISIBLE);
            middle.setVisibility(View.VISIBLE);
        }
        //--**

        //---------empty btn action---//
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewIntent = new Intent(getApplicationContext(), AddNewActivity.class);
                startActivity(addNewIntent);
            }
        });


        //------------set expenses list----------//
        ListView lv = (ListView) findViewById(R.id.expenses_list);
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, expensesList, R.layout.list_row,new String[]{"category","total","updated_at"}, new int[]{R.id.category, R.id.total, R.id.updated_at});
        lv.setAdapter(adapter);

        ///-------------show total expenses------//
        float grandtotal = db.calTotal();
        TextView totalText = (TextView) findViewById(R.id.grandTotal);
        totalText.setText("Total Rs: "+String.format("%.02f", grandtotal));


        //--------------click on list view item----------//

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),ViewItemActivity.class);
                intent.putExtra("map",expensesList.get(i));
                startActivity(intent);

            }
        });

    }

    //---------------------------menu bar--------------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add:
                //Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                Intent addNewIntent = new Intent(getApplicationContext(), AddNewActivity.class);
                startActivity(addNewIntent);
                return true;

            case R.id.chart:
                Intent graphIntent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivity(graphIntent);
                return true;

            case R.id.quote:
                Intent quoteIntent = new Intent(getApplicationContext(), QuoteActivity.class);
                startActivity(quoteIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //------------------------------//


}