package com.example.week4weekend;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    public static final String ZIP_CODE = "ZIP_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        int zipcode = sharedPreferences.getInt(ZIP_CODE, -1);
        if (zipcode == -1) {
            askForZipCode();
        }
    }

    public void askForZipCode() {
        ZipcodeDialogFragment zipDialog = new ZipcodeDialogFragment();
        zipDialog.show(getSupportFragmentManager(), "dialog");
    }
}
