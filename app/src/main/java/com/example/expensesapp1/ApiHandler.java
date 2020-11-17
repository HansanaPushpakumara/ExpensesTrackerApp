package com.example.expensesapp1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiHandler extends AsyncTask<Void, Void, Void> {

    String data = "";
    String quote = "";
    String author = "";


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://zenquotes.io/api/random");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

           /* JSONObject reader = new JSONObject(data);
            result = reader.getString("cases");*/

            JSONArray reader = new JSONArray(data);

            JSONObject q = reader.getJSONObject(0);

            quote = q.getString("q");
            author = q.getString("a");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        QuoteActivity.quoteTxt.setText("\""+this.quote+"\"");
        QuoteActivity.authorTxt.setText("~"+this.author+"~");


    }
}