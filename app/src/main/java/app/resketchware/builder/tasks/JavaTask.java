package app.resketchware.builder.tasks;

import android.util.Log;

import app.resketchware.R;
import app.resketchware.builder.Task;
import app.resketchware.builder.exceptions.CompilationFailedException;
import app.resketchware.builder.listeners.ProgressListener;
import app.resketchware.ui.models.Project;
import app.resketchware.utils.ContextUtil;
import app.resketchware.utils.FileUtil;
import app.resketchware.utils.SketchwareUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JavaTask extends Task {
  public JavaTask(Project project, ProgressListener listener) {
    super(project, listener);
  }

  @Override
  public String getName() {
    return ContextUtil.getString(R.string.compiler_java_message);
  }

  @Override
  public void prepare() throws IOException {}

  @Override
  public void run() throws IOException, CompilationFailedException {
    try (EclipseOutOutputStream outOutputStream = new EclipseOutOutputStream();
       PrintWriter outWriter = new PrintWriter(outOutputStream);
       EclipseErrOutputStream errOutputStream = new EclipseErrOutputStream();
       PrintWriter errWriter = new PrintWriter(errOutputStream)) {

      ArrayList<String> args = new ArrayList<>();
      args.add("-1.7");
      args.add("-nowarn");
      args.add("-deprecation");
      args.add("-d");
      args.add(project.getCompiledClassesDirectory().getAbsolutePath());
      args.add("-cp");
      args.add(SketchwareUtil.getClasspath());
      args.add("-proc:none");
      args.add(project.getJavaDirectory().getAbsolutePath());
      args.add(project.getRJavaDirectory().getAbsolutePath());
      String pathJava = project.getDataJavaDirectory().getAbsolutePath();
      if (FileUtil.isExists(pathJava)) {
        args.add(pathJava);
      }

      /* Avoid "package ;" line in that file causing issues while compiling */
      File rJavaFileWithoutPackage = new File(project.getRJavaDirectory().getAbsolutePath(), "R.java");
      if (rJavaFileWithoutPackage.exists() && !rJavaFileWithoutPackage.delete()) {
        Log.w("JavaTask", "Failed to delete file " + rJavaFileWithoutPackage.getAbsolutePath());
      }

      org.eclipse.jdt.internal.compiler.batch.Main main = new org.eclipse.jdt.internal.compiler.batch.Main(outWriter, errWriter, false, null, null);
      Log.d("JavaTask", "Running Eclipse compiler with these arguments: " + args);
      main.compile(args.toArray(new String[0]));

      Log.d("JavaTask", "System.out of Eclipse compiler: " + outOutputStream.getOut());
      if (main.globalErrorsCount <= 0) {
        Log.d("JavaTask", "System.err of Eclipse compiler: " + errOutputStream.getOut());
      } else {
        Log.e("JavaTask", "Failed to compile Java files");
        throw new CompilationFailedException(errOutputStream.getOut());
      }
    }
  }

  class EclipseOutOutputStream extends OutputStream {
    private final StringBuffer mBuffer = new StringBuffer();

    @Override
    public void write(int b) {
      mBuffer.append((char) b);
    }

    public String getOut() {
      return mBuffer.toString();
    }
  }

  class EclipseErrOutputStream extends OutputStream {
    private final StringBuffer mBuffer = new StringBuffer();

    @Override
    public void write(int b) {
      mBuffer.append((char) b);
    }

    public String getOut() {
      return mBuffer.toString();
    }
  }
}
