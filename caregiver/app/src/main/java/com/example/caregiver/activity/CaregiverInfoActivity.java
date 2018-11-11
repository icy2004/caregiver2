package com.example.caregiver.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.caregiver.R;
import com.example.caregiver.fragment.BoardFragment;
import com.example.caregiver.fragment.HomeFragment;
import com.example.caregiver.fragment.LetterFragment;
import com.example.caregiver.fragment.MyPageFragment;
import com.example.caregiver.fragment.ReportFragment;
import com.example.caregiver.model.Caregiver;

import java.util.HashMap;

public class CaregiverInfoActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private RadioGroup tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Caregiver caregiver = (Caregiver) getIntent().getSerializableExtra("caregiver");


        TextView text_name = findViewById(R.id.text_name);
        TextView text_age = findViewById(R.id.text_age);
        TextView text_gender = findViewById(R.id.text_gender);
        TextView text_prof = findViewById(R.id.text_prof);
        TextView text_exp = findViewById(R.id.text_exp);
        TextView text_locale = findViewById(R.id.text_locale);
        TextView text_pay = findViewById(R.id.text_pay);
        RatingBar rating = findViewById(R.id.rating);

        text_name.setText(caregiver.getName());
        text_age.setText(caregiver.getAge() + "");
        text_gender.setText(caregiver.getGender());
        text_prof.setText(caregiver.getPro());
        text_exp.setText("경력 : " + caregiver.getExp() + "년 이상");
        text_locale.setText("가능지역 : " + caregiver.getLocale());
        text_pay.setText("시급 정보 : " + caregiver.getPay());
        rating.setRating(caregiver.getAvg());
    }
}
