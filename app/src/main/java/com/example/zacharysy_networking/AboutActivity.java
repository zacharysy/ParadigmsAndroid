package com.example.zacharysy_networking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private TextView aboutTextView;
    private Button openWebpageButton;
    private Button openMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutTextView = (TextView) findViewById(R.id.aboutText);
        openWebpageButton = (Button) findViewById(R.id.openWebpageButton);
        openMapButton = (Button) findViewById(R.id.openMapButton);

        final String urlString = "http://www.nd.edu/";

        openWebpageButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        openWebPage(urlString);
                    }
                }
        );

        openMapButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        openMap();
                    }
                }
        );

    }

    public void openWebPage(String urlString){
        Uri webpage = Uri.parse(urlString);
        Intent openWebpageIntent = new Intent(Intent.ACTION_VIEW, webpage);

        try{
            startActivity(openWebpageIntent);
        }catch (ActivityNotFoundException e){
            return;
        }
    }

    public void openMap(){
        String addressString = "University of Notre Dame, IN";
        Uri addressUri = Uri.parse("geo:0,0").buildUpon().appendQueryParameter("q", addressString).build();

        Intent openMapIntent = new Intent(Intent.ACTION_VIEW);
        openMapIntent.setData(addressUri);

        try{
            startActivity(openMapIntent);
        }catch (ActivityNotFoundException e){
            return;
        }
    }
}