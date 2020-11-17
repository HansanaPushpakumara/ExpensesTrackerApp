package com.example.expensesapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphActivity extends AppCompatActivity {
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    ArrayList PieEntryLabels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        pieChart = findViewById(R.id.pieChart);
        getEntries();
        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(5f);
    }
    private void getEntries() {
        DbHandler db = new DbHandler(this);
        float grandtotal = db.calTotal();
        final ArrayList<HashMap<String, String>> expensesList = db.GetExpenses();

        pieEntries = new ArrayList<>();

        for (int i = 0; i < expensesList.size(); i++) {
            HashMap<String, String> singleRow = expensesList.get(i);
            float percentage = (Float.parseFloat(singleRow.get("total"))/grandtotal)*100;
            pieEntries.add(new PieEntry(percentage, singleRow.get("category")));
        }

       /* pieEntries.add(new PieEntry(30, "food"));
        pieEntries.add(new PieEntry(20, "travel"));
        pieEntries.add(new PieEntry(50, "hotel"));
       /* pieEntries.add(new PieEntry(8f, 3));
        pieEntries.add(new PieEntry(7f, 4));
        pieEntries.add(new PieEntry(3f, 5));*/
    }
}