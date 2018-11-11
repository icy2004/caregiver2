package com.example.caregiver.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.caregiver.App;
import com.example.caregiver.R;
import com.example.caregiver.activity.CaregiverInfoActivity;
import com.example.caregiver.activity.MainActivity;
import com.example.caregiver.activity.SearchActivity;
import com.example.caregiver.model.Caregiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {

    private Spinner sp_1;
    private Spinner sp_2;
    private Spinner sp_3;
    private ListView listview;
    private SwipeRefreshLayout refresh_view;
    private Button btn_search;

    private CaregiverListAdater adater;

    private List<Caregiver> items;
    private List<Caregiver> visibleItems;

    private String selSort;
    private String selProf;
    private String selGender;

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_home, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sp_1 = getView().findViewById(R.id.sp_1);
        sp_2 = getView().findViewById(R.id.sp_2);
        sp_3 = getView().findViewById(R.id.sp_3);
        listview = getView().findViewById(R.id.listview);
        refresh_view = getView().findViewById(R.id.refresh_view);
        btn_search = getView().findViewById(R.id.btn_search);

        sp_1.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.arr_1)));
        sp_2.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.arr_2)));
        sp_3.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.arr_3)));

        items = ((App)getActivity().getApplication()).getCaregiverArray();
        visibleItems = new ArrayList<>();
        visibleItems.addAll(items);

        adater = new CaregiverListAdater();

        listview.setAdapter(adater);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                refresh_view.setEnabled(firstVisibleItem == 0);
            }
        });

        refresh_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_view.setRefreshing(false);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

        sp_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selSort = adapterView.getSelectedItem().toString();
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selProf = adapterView.getSelectedItem().toString();
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selGender = adapterView.getSelectedItem().toString();
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void refresh() {
        visibleItems.clear();
        if(TextUtils.equals("전체", selProf) && TextUtils.equals("전체", selGender)) {
            visibleItems.addAll(items);
        } else {
            if(TextUtils.equals("전체", selProf)) {
                for(Caregiver caregiver : items) {
                    if(TextUtils.equals(caregiver.getGender(), selGender)) {
                        visibleItems.add(caregiver);
                    }
                }
            } else {
                for(Caregiver caregiver : items) {
                    if(TextUtils.equals(caregiver.getPro(), selProf)) {
                        if(TextUtils.equals(caregiver.getGender(), selGender) || TextUtils.equals(selGender, "전체")) {
                            visibleItems.add(caregiver);
                        }
                    }

                    if(TextUtils.equals(caregiver.getPro(), "모두 가능")) {
                        if(TextUtils.equals(caregiver.getGender(), selGender) || TextUtils.equals(selGender, "전체")) {
                            visibleItems.add(caregiver);
                        }
                    }
                }
            }
        }
        if (TextUtils.equals(selSort, "신규순")) {
            Collections.sort(visibleItems, new Comparator<Caregiver>() {
                @Override
                public int compare(Caregiver t1, Caregiver t2) {
                    if (t1.getSeq() > t2.getSeq()) {
                        return 1;
                    } else if (t1.getSeq() < t2.getSeq()){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else if(TextUtils.equals(selSort, "평점순")) {
            Collections.sort(visibleItems, new Comparator<Caregiver>() {
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
        } else if(TextUtils.equals(selSort, "시급낮은순")) {
            Collections.sort(visibleItems, new Comparator<Caregiver>() {
                @Override
                public int compare(Caregiver t1, Caregiver t2) {
                    if (t1.getPay() > t2.getPay()) {
                        return 1;
                    } else if (t1.getPay() < t2.getPay()){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else {
            Collections.sort(visibleItems, new Comparator<Caregiver>() {
                @Override
                public int compare(Caregiver t1, Caregiver t2) {
                    if (t1.getPay() < t2.getPay()) {
                        return 1;
                    } else if (t1.getPay() > t2.getPay()){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }
        adater.notifyDataSetChanged();
    }

    private class CaregiverListAdater extends BaseAdapter {

        @Override
        public int getCount() {
            return visibleItems.size();
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
                view = View.inflate(getContext(), R.layout.caregiver_list_item, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            final Caregiver caregiver = visibleItems.get(i);
            viewHolder.text_name.setText(caregiver.getName());
            viewHolder.text_age.setText(caregiver.getAge() + "");
            viewHolder.text_gender.setText(caregiver.getGender());
            viewHolder.text_prof.setText(caregiver.getPro());
            viewHolder.text_exp.setText("경력 : " + caregiver.getExp() + "년 이상");

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CaregiverInfoActivity.class);
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
