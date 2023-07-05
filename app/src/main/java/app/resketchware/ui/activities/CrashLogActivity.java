package app.resketchware.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import app.resketchware.R;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CrashLogActivity extends AppCompatActivity {

    public static final String ERROR_EXTRA = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayError();
    }

    private void displayError() {
        Intent intent = getIntent();
        if (intent != null) {
            String error = intent.getStringExtra(ERROR_EXTRA);
            if (error != null) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("An error occurred")
                        .setMessage(error)
                        .show();
            }
        }
    }
}