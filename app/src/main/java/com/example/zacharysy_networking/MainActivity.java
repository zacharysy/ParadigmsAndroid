package com.example.zacharysy_networking;

import com.example.zacharysy_networking.utilities.NetworkUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText textField;
    private Button searchButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeNetworkSearchQuery();

        // Connect to View
        textView = (TextView) findViewById(R.id.textView);
        textField = (EditText) findViewById(R.id.textField);
        searchButton = (Button) findViewById(R.id.searchButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        // Event Listeners
        resetButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        makeNetworkSearchQuery();
                    }
                }
        );

        searchButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        String query = textField.getText().toString().toLowerCase();
                        String countries = textView.getText().toString();
                        String[] countriesList = countries.split("\n");
                        for(String country: countriesList){
                            if(country.toLowerCase().equals(query)){
                                textView.setText(country);
                                break;
                            }else{
                                textView.setText("No results match.");
                            }
                        }
                    }
                }
        );

    }

    public void makeNetworkSearchQuery(){
        new FetchNetworkData().execute();
    }

    public class FetchNetworkData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            URL searchURL = NetworkUtils.bulidCountriesUrl();
            String responseString = null;

            try{
                responseString = NetworkUtils.getResponseFromUrl(searchURL);
            }catch(Exception e){
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String responseData) {
            super.onPostExecute(responseData);
            ArrayList<String> countries = NetworkUtils.parseCountriesJson(responseData);

            try{
                for(String name: countries){
                    textView.append("\n\n" + name);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemSelected = item.getItemId();
        if(menuItemSelected == R.id.menu_about){
            Class destinationActivity = AboutActivity.class;

            Intent startAboutActivityIntent = new Intent(MainActivity.this, destinationActivity);
            String msg = textField.getText().toString();
            startAboutActivityIntent.putExtra(Intent.EXTRA_TEXT, msg);

            startActivity(startAboutActivityIntent);
            Log.d("info", "About Activity launched");
        }

        return true;
    }
}