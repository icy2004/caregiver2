package com.example.caregiver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.caregiver.R;
import com.example.caregiver.fragment.HomeFragment;
import com.example.caregiver.model.Caregiver;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class ResultActivity extends AppCompatActivity {

    private ListView listview;

    private CaregiverListAdater adater;
    private List<Caregiver> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listview = findViewById(R.id.listview);
        items = (List<Caregiver>) getIntent().getSerializableExtra("caregivers");
        adater = new CaregiverListAdater();

        listview.setAdapter(adater);
    }

    private class CaregiverListAdater extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Caregiver getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder viewHolder;
            if(view == null) {
                view = View.inflate(ResultActivity.this, R.layout.caregiver_list_item, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            final Caregiver caregiver = items.get(i);
            viewHolder.text_name.setText(caregiver.getName());
            viewHolder.text_age.setText(caregiver.getAge() + "");
            viewHolder.text_gender.setText(caregiver.getGender());
            viewHolder.text_prof.setText(caregiver.getPro());
            viewHolder.text_exp.setText("경력 : " + caregiver.getExp() + "년 이상");

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, CaregiverInfoActivity.class);
                    intent.putExtra("caregiver", caregiver);
                    startActivity(intent);
                }
            });
            return view;
        }

        class ViewHolder {
            TextView text_name;
            TextView text_age;
            TextView text_gender;
            TextView text_prof;
            TextView text_exp;
            ViewHolder(View view) {
                text_name = view.findViewById(R.id.text_name);
                text_age = view.findViewById(R.id.text_age);
                text_gender = view.findViewById(R.id.text_gender);
                text_prof = view.findViewById(R.id.text_prof);
                text_exp = view.findViewById(R.id.text_exp);
            }
        }
    }
}
