package com.example.expensesapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewActivity extends AppCompatActivity {

    EditText category, expense;
    Button saveBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        category = (EditText)findViewById(R.id.newCategory);
        expense = (EditText)findViewById(R.id.newExpense);
        saveBtn = (Button)findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get inputs
                String NewCategory = category.getText().toString();
                String NewExpense = expense.getText().toString();

                Validator valobj = new Validator(); //--check validation
                if(valobj.validate(NewCategory,NewExpense)){
                    Toast.makeText(getApplicationContext(), "Invalid Input",Toast.LENGTH_SHORT).show();
                }
                else{
                    float newExpense = Float.parseFloat(NewExpense);
                    NewExpense = String.format("%.02f", newExpense);

                    DbHandler dbHandler = new DbHandler(AddNewActivity.this);

                    dbHandler.addCategory(NewCategory,NewExpense);

                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);


                    Toast.makeText(getApplicationContext(), "New category created ",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}