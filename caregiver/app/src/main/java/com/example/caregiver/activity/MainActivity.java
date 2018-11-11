package com.example.caregiver.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.caregiver.R;
import com.example.caregiver.fragment.BoardFragment;
import com.example.caregiver.fragment.HomeFragment;
import com.example.caregiver.fragment.LetterFragment;
import com.example.caregiver.fragment.MyPageFragment;
import com.example.caregiver.fragment.ReportFragment;
import com.example.caregiver.model.Caregiver;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private RadioGroup tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_pager = findViewById(R.id.view_pager);
        tab = findViewById(R.id.tab);

        view_pager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        view_pager.setCurrentItem(2, false);

        tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.tab_1:
                        view_pager.setCurrentItem(0, false);
                        break;
                    case R.id.tab_2:
                        view_pager.setCurrentItem(1, false);
                        break;
                    case R.id.tab_3:
                        view_pager.setCurrentItem(2, false);
                        break;
                    case R.id.tab_4:
                        view_pager.setCurrentItem(3, false);
                        break;
                    case R.id.tab_5:
                        view_pager.setCurrentItem(4, false);
                        break;
                }
            }
        });

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                TabAdapter adapter = (TabAdapter) view_pager.getAdapter();
                if(i == 4) {
                    MyPageFragment myPageFragment = (MyPageFragment) adapter.getItem(i);
                    myPageFragment.buttonRefresh();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });
    }

    private class TabAdapter extends FragmentPagerAdapter {

        private HashMap<Integer, Fragment> fragments;

        public TabAdapter(FragmentManager fm) {
            super(fm);
            fragments = new HashMap<>();
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = fragments.get(i);
            if (fragment == null) {
                switch (i) {
                    case 0:
                        fragment = ReportFragment.newInstance();
                        break;
                    case 1:
                        fragment = BoardFragment.newInstance();
                        break;
                    case 2:
                        fragment = HomeFragment.newInstance();
                        break;
                    case 3:
                        fragment = LetterFragment.newInstance();
                        break;
                    case 4:
                        fragment = MyPageFragment.newInstance();
                        break;
                }
                fragments.put(i, fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
