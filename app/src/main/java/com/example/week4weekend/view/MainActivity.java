package com.example.week4weekend.view;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.week4weekend.R;
import com.example.week4weekend.view.fragments.CurrentWeatherFragment;
import com.example.week4weekend.view.fragments.WeeklyWeatherFragment;
import com.example.week4weekend.view.fragments.ZipcodeDialogFragment;

import static com.example.week4weekend.utils.CommonConstants.SHARED_PREFERENCES;
import static com.example.week4weekend.utils.CommonConstants.SHARED_ZIP_DEFAULT;
import static com.example.week4weekend.utils.CommonConstants.ZIP_CODE_PREFERENCES;
import static com.example.week4weekend.utils.CommonConstants.ZIP_CODE_BUNDLE;

public class MainActivity extends AppCompatActivity
        implements ZipcodeDialogFragment.OnDialogOptionSelected,
        CurrentWeatherFragment.OnInteractionListener {

    SharedPreferences sharedPreferences;
    CurrentWeatherFragment hourlyWeatherFragment;
    WeeklyWeatherFragment weeklyWeatherFragment;
    FragmentManager fragmentManager;
    int zipCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        checkZipCode();
    }

    private void checkZipCode() {
        zipCode = sharedPreferences.getInt(ZIP_CODE_PREFERENCES, SHARED_ZIP_DEFAULT);
        if (zipCode == SHARED_ZIP_DEFAULT) {
            askForZipCode();
        } else {
            callFragments(zipCode);
        }
    }

    public void askForZipCode() {
        ZipcodeDialogFragment zipDialog = new ZipcodeDialogFragment();
        zipDialog.show(getSupportFragmentManager(), "dialog");
    }

    // Don't let the user start app without entering zip code
    @Override
    public void onCancelSelected() {
        Toast.makeText(this, "You need to enter a valid zip code first", Toast.LENGTH_SHORT).show();
        askForZipCode();
    }

    @Override
    public void onDoneSelected(int zipCode) {
        callFragments(zipCode);
    }

    private void callFragments(int zipCode) {
        hourlyWeatherFragment = CurrentWeatherFragment.newInstance();
        weeklyWeatherFragment = WeeklyWeatherFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putInt(ZIP_CODE_BUNDLE, zipCode);

        hourlyWeatherFragment.setArguments(bundle);
        weeklyWeatherFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentHourly, hourlyWeatherFragment)
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentWeekly, weeklyWeatherFragment)
                .commit();
    }

    @Override
    public void onScaleChange(boolean isCelsius) {
        weeklyWeatherFragment.scaleChanged(isCelsius);
    }

    @Override
    public void onZipCodeNotFound() {
        sharedPreferences.edit().putInt(ZIP_CODE_PREFERENCES, SHARED_ZIP_DEFAULT).apply();
        Toast.makeText(this, "Invalid Zip Code", Toast.LENGTH_LONG).show();
        askForZipCode();
    }
}
