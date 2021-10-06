package com.example.careapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.careapp.AchieveActivity;
import com.example.careapp.AdviceActivity;
import com.example.careapp.ArchivesActivity;
import com.example.careapp.DialogActivity;
import com.example.careapp.MonitorActivity;
import com.example.careapp.NoteActivity;
import com.example.careapp.PlanActivity;
import com.example.careapp.R;
import com.example.careapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private ImageButton _img_btn_archives,_img_btn_hospital,_img_btn_monitor,
            _img_btn_advice,_img_btn_note,_img_btn_plan,_img_btn_achieve;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        _img_btn_archives=(ImageButton)root.findViewById(R.id.imageButton_archives);
        _img_btn_archives.setOnClickListener(new ButtonListener());
        _img_btn_hospital=(ImageButton)root.findViewById(R.id.imageButton_hospital);
        _img_btn_hospital.setOnClickListener(new ButtonListener());
        _img_btn_monitor=(ImageButton)root.findViewById(R.id.imageButton_monitor);
        _img_btn_monitor.setOnClickListener(new ButtonListener());
        //_img_btn_advice=(ImageButton)root.findViewById(R.id.imageButton_advice);
        //_img_btn_advice.setOnClickListener(new ButtonListener());
        _img_btn_note=(ImageButton)root.findViewById(R.id.imageButton_note);
        _img_btn_note.setOnClickListener(new ButtonListener());
        _img_btn_plan=(ImageButton)root.findViewById(R.id.imageButton_plan);
        _img_btn_plan.setOnClickListener(new ButtonListener());
        _img_btn_achieve=(ImageButton)root.findViewById(R.id.imageButton_achievement);
        _img_btn_achieve.setOnClickListener(new ButtonListener());
        return root;
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton_archives:
                    Intent intent_archives= new Intent(getActivity(),ArchivesActivity.class);
                    startActivity(intent_archives);
                    break;
                case R.id.imageButton_hospital:
                    Intent intent_hospital= new Intent(getActivity(), DialogActivity.class);
                    startActivity(intent_hospital);
                    break;
                case R.id.imageButton_monitor:
                    Intent intent_monitor= new Intent(getActivity(), MonitorActivity.class);
                    startActivity(intent_monitor);
                    break;
                case R.id.imageButton_note:
                    Intent intent_note= new Intent(getActivity(), NoteActivity.class);
                    startActivity(intent_note);
                    break;
                case R.id.imageButton_plan:
                    Intent intent_plan= new Intent(getActivity(), PlanActivity.class);
                    startActivity(intent_plan);
                    break;
                case R.id.imageButton_achievement:
                    Intent intent_achievement= new Intent(getActivity(), AchieveActivity.class);
                    startActivity(intent_achievement);
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}