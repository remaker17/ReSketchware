package app.resketchware.services;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.viewmodels.CompilerViewModel;

public class CompilerServiceConnection implements ServiceConnection {
  private boolean mCompiling;

  private final CompilerViewModel mCompilerViewModel;
  private CompilerService mService;

  public CompilerServiceConnection(@NonNull CompilerViewModel viewModel) {
    mCompilerViewModel = viewModel;
  }

  public boolean isCompiling() {
    return mCompiling;
  }

  @Override
  public void onServiceConnected(ComponentName name, IBinder binder) {
    mService = ((CompilerService.CompilerBinder) binder).getCompilerService();
    if (mService == null) {
      mCompilerViewModel.setMessage("CompilerService is null");
      return;
    }

    mCompiling = true;
    mService.setOnResultListener((success, message) -> {
      Log.d("CompilerServiceConnection", message);
      mCompilerViewModel.setMessage(message);
      if (success) {
        requestToInstallApk(mService);
      }
    });
    mService.compile(mCompilerViewModel.getProject().getValue(), message -> {
      mCompilerViewModel.setMessage(message);
    });
  }

  @Override
  public void onServiceDisconnected(ComponentName name) {
    mService = null;
    // mCompilerViewModel.setMessage(null);
    // mCompilerViewModel.setCompiling(false);
    mCompiling = false;
  }

  private void requestToInstallApk(Context context) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    if (Build.VERSION.SDK_INT >= 24) {
      Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", mCompilerViewModel.getProject().getValue().getFinalToInstallApkDirectory());
      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
      intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
      intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
    } else {
      intent.setDataAndType(Uri.fromFile(mCompilerViewModel.getProject().getValue().getFinalToInstallApkDirectory()), "application/vnd.android.package-archive");
    }

    try {
      context.startActivity(intent);
    } catch (ActivityNotFoundException e) {
      e.printStackTrace();
    }
  }
}
