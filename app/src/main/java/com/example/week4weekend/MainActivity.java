package com.example.week4weekend;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements ZipcodeDialogFragment.OnDialogOptionSelected {

    SharedPreferences sharedPreferences;
    HourlyWeatherFragment hourlyWeatherFragment;
    FragmentManager fragmentManager;
    int zipcode;

    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    public static final String ZIP_CODE = "ZIP_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        checkZipCode();
    }

    private void checkZipCode() {
        zipcode = sharedPreferences.getInt(ZIP_CODE, -1);
        if (zipcode == -1) {
            askForZipCode();
        } else {
            callFragment(zipcode);
        }
    }

    public void askForZipCode() {
        ZipcodeDialogFragment zipDialog = new ZipcodeDialogFragment();
        zipDialog.show(getSupportFragmentManager(), "dialog");
    }

    // Don't let the user start app without entering zip code
    @Override
    public void onCancelSelected() {
        Toast.makeText(this, "You need to enter your zip code first", Toast.LENGTH_SHORT).show();
        askForZipCode();
    }

    @Override
    public void onDoneSelected(int zipCode) {
        callFragment(zipCode);
    }

    private void callFragment(int zipCode) {
        hourlyWeatherFragment = HourlyWeatherFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("zipcode", zipCode);
        hourlyWeatherFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentHourly, hourlyWeatherFragment)
                .commit();
    }
}
