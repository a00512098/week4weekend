package com.example.week4weekend;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.week4weekend.MainActivity.SHARED_PREFERENCES;
import static com.example.week4weekend.MainActivity.ZIP_CODE;

public class ZipcodeDialogFragment extends DialogFragment {

    EditText dialogZipcode;
    SharedPreferences sharedPreferences;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View customLayout = inflater.inflate(R.layout.dialog_zipcode, null);
        dialogZipcode = customLayout.findViewById(R.id.dialogZipcode);
        builder.setView(customLayout)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        saveZipCode();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ZipcodeDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void saveZipCode() {
        int zipCode = Integer.parseInt(dialogZipcode.getText().toString());
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ZIP_CODE, zipCode);
        editor.apply();
        Toast.makeText(getContext(), "Zip Code Saved", Toast.LENGTH_SHORT).show();
        Log.d("Log.d", "Zip Code: " + zipCode);
    }
}
