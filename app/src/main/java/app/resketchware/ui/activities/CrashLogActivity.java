package app.resketchware.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import app.resketchware.R;

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
        new AlertDialog.Builder(this)
            .setTitle("An error occurred")
            .setMessage(error)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
              finishAffinity();
              dialog.dismiss();
            })
            .show();
      }
    }
  }
}