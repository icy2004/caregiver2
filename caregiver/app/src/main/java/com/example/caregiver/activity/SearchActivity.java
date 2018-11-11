package com.example.caregiver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.caregiver.App;
import com.example.caregiver.R;
import com.example.caregiver.fragment.BoardFragment;
import com.example.caregiver.fragment.HomeFragment;
import com.example.caregiver.fragment.LetterFragment;
import com.example.caregiver.fragment.MyPageFragment;
import com.example.caregiver.fragment.ReportFragment;
import com.example.caregiver.model.Caregiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private RadioButton check_m;
    private RadioGroup age_group;

    private int ageMin = 20;
    private int ageMax = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        check_m = findViewById(R.id.check_m);
        age_group = findViewById(R.id.age_group);

        age_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.age_1:
                        ageMin = 20;
                        ageMax = 30;
                        break;
                    case R.id.age_2:
                        ageMin = 30;
                        ageMax = 40;
                        break;
                    case R.id.age_3:
                        ageMin = 40;
                        ageMax = 50;
                        break;
                    case R.id.age_4:
                        ageMin = 50;
                        ageMax = 60;
                        break;
                    case R.id.age_5:
                        ageMin = 60;
                        ageMax = 70;
                        break;
                }
            }
        });
    }

    public void onClickConfirm(View view) {
        ArrayList<Caregiver> caregivers = new ArrayList<>();
        ArrayList<Caregiver> results = new ArrayList<>();
        ArrayList<Caregiver> items = (ArrayList<Caregiver>) ((App)getApplication()).getCaregiverArray();
        String sido = ((App)getApplication()).getSido();
        String gugun = ((App)getApplication()).getGugun();
        String disease = ((App)getApplication()).getDisease();
        String gender = check_m.isChecked() ? "남" : "여";

        for(Caregiver caregiver : items) {
            if(TextUtils.equals(caregiver.getPro(), disease)
                    && TextUtils.equals(caregiver.getGender(), gender)
                    && caregiver.getLocale().contains(sido)
                    && caregiver.getLocale().contains(gugun)
                    && caregiver.getAge() >= ageMin && caregiver.getAge() <= ageMax) {
                caregivers.add(caregiver);
            }

            if(TextUtils.equals(caregiver.getPro(), "모두 가능")
                    && TextUtils.equals(caregiver.getGender(), gender)
                    && caregiver.getLocale().contains(sido)
                    && caregiver.getLocale().contains(gugun)
                    && caregiver.getAge() >= ageMin && caregiver.getAge() <= ageMax) {
                caregivers.add(caregiver);
            }
        }

        if(caregivers.size() < 5) {
            for(Caregiver caregiver : items) {
                boolean b = false;
                for(Caregiver ca : caregivers) {
                    if(ca == caregiver) {
                        b = true;
                    }
                }
                if(b) continue;
                int x = ageMin - 10;
                int y = ageMax + 10;
                if(TextUtils.equals(caregiver.getPro(), disease)
                        && TextUtils.equals(caregiver.getGender(), gender)
                        && caregiver.getLocale().contains(sido)
                        && caregiver.getLocale().contains(gugun)
                        && caregiver.getAge() >= x && caregiver.getAge() <= y) {
                    caregivers.add(caregiver);
                }

                if(TextUtils.equals(caregiver.getPro(), "모두 가능")
                        && TextUtils.equals(caregiver.getGender(), gender)
                        && caregiver.getLocale().contains(sido)
                        && caregiver.getLocale().contains(gugun)
                        && caregiver.getAge() >= x && caregiver.getAge() <= y) {
                    caregivers.add(caregiver);
                }
            }
        }

        Collections.sort(caregivers, new Comparator<Caregiver>() {
            @Override
            public int compare(Caregiver t1, Caregiver t2) {
                if (t1.getAvg() < t2.getAvg()) {
                    return 1;
                } else if (t1.getAvg() > t2.getAvg()){
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        if(caregivers.size() > 5) {
            for(int i = 0; i < 5; i++) {
                results.add(caregivers.get(i));
            }
        } else {
            results.addAll(caregivers);
        }

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("caregivers", results);
        startActivity(intent);
    }
}
