package app.resketchware.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import app.resketchware.R;

import com.google.android.material.transition.MaterialSharedAxis;

public class ProjectsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialSharedAxis enterTransition = new MaterialSharedAxis(MaterialSharedAxis.Y, false);
        MaterialSharedAxis exitTransition = new MaterialSharedAxis(MaterialSharedAxis.Y, true);
        enterTransition.setDuration(200);
        exitTransition.setDuration(200);
        setEnterTransition(enterTransition);
        setExitTransition(exitTransition);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((android.widget.TextView) view.findViewById(R.id.test)).setOnClickListener(v -> {
            ((android.widget.TextView) v).setText("Clicked");
        });
    }
}