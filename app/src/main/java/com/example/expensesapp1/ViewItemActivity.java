package com.example.expensesapp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ViewItemActivity extends AppCompatActivity {

    DbHandler dbhand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>)intent.getSerializableExtra("map");
        Log.v("HashMapTest", hashMap.get("id"));

        final String keyId = hashMap.get("id");
        final String expense = hashMap.get("total");
        final String category = hashMap.get("category");
        String created_at = hashMap.get("created_at");
        String updated_at = hashMap.get("updated_at");

        TextView expenseTxt = (TextView) findViewById(R.id.total);
        TextView catTxt = (TextView) findViewById(R.id.category);
        TextView from = (TextView) findViewById(R.id.fromTxt);
        TextView toTxt = (TextView) findViewById(R.id.toTxt);
        final EditText newExpense = (EditText) findViewById(R.id.newExpense);

        expenseTxt.setText("Rs: "+expense);//--category expense
        catTxt.setText(category);//--category
        from.setText(created_at); //--from time period
        toTxt.setText(updated_at); //--to time period


        Button rmvBtn = (Button) findViewById(R.id.removeBtn);
        Button addBtn = (Button) findViewById(R.id.addBtn);
        Button sbrBtn = (Button) findViewById(R.id.substrBtn);


        final DbHandler db = new DbHandler(this);


        //----------add-button------//
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Validator valobj = new Validator(); //--check validation
                if(valobj.validate(newExpense.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Empty Input",Toast.LENGTH_SHORT).show();
                }
                else{
                    float updatedExpense = Float.parseFloat(expense)+Float.parseFloat(newExpense.getText().toString());
                    String updatedExpenseTxt = String.format("%.02f", updatedExpense);

                    db.UpdateExpenseDetails(category,updatedExpenseTxt,Integer.parseInt(keyId));

                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    Toast.makeText(getApplicationContext(), "Expense Updated",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //----------substract-button------//
        sbrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Validator valobj = new Validator(); //--check validation
                if(valobj.validate(newExpense.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Empty Input",Toast.LENGTH_SHORT).show();
                }
                else {
                    float updatedExpense = Float.parseFloat(expense) - Float.parseFloat(newExpense.getText().toString());
                    String updatedExpenseTxt = String.format("%.02f", updatedExpense);

                    db.UpdateExpenseDetails(category, updatedExpenseTxt, Integer.parseInt(keyId));

                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    Toast.makeText(getApplicationContext(), "Expense Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //----------remove-button------//
        rmvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(keyId);
            }
        });



    }


    //---remove from db------
    public void removeData(String id){
        DbHandler ob = new DbHandler(this);
        ob.DeleteExpense(Integer.parseInt(id));
    }


    //---open confirm box-----
    public void open(final String keyId2){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Remove Expense?");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
        String id = keyId2;
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                removeData(keyId2);
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                Toast.makeText(getApplicationContext(), "Expense Removed",Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    //--confirm end



}
