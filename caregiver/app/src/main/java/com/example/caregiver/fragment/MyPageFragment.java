package com.example.caregiver.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.caregiver.App;
import com.example.caregiver.R;
import com.example.caregiver.model.ZipCode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyPageFragment extends Fragment {
    private static final int STATUS_FINISH = 1;
    private static final int STATUS_READAY = 2;
    private static final int STATUS_PROCESS= 3;

    private Spinner sp_1;
    private Spinner sp_2;
    private Spinner sp_3;
    private Button btn_1;
    private Button btn_2;

    private List<ZipCode> zipCodeArray;

    public static Fragment newInstance() {
        return new MyPageFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_mypage, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        zipCodeArray = new ArrayList<>();

        sp_1 = getView().findViewById(R.id.sp_1);
        sp_2 = getView().findViewById(R.id.sp_2);
        sp_3 = getView().findViewById(R.id.sp_3);
        btn_1 = getView().findViewById(R.id.btn_1);
        btn_2 = getView().findViewById(R.id.btn_2);

        String[] sidoArr = new String[((App)getActivity().getApplication()).getSidoArray().size()];
        ((App)getActivity().getApplication()).getSidoArray().toArray(sidoArr);

        sp_1.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.arr_4)));
        sp_2.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, sidoArr));
        sp_3.setAdapter(new ArrayAdapter<ZipCode>(getContext(), android.R.layout.simple_dropdown_item_1line, zipCodeArray));

        sp_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((App)getActivity().getApplication()).setDisease(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sido = (String) adapterView.getSelectedItem();
                ((App)getActivity().getApplication()).setSido(sido);
                List<ZipCode> zipCodes = ((App)getActivity().getApplication()).getZipCodeArray();
                zipCodeArray.clear();
                for(ZipCode zipCode : zipCodes) {
                    if(TextUtils.equals(zipCode.getSido(), sido)) {
                        zipCodeArray.add(zipCode);
                    }
                }
                if(zipCodeArray.size() > 0) {
                    ((App) getActivity().getApplication()).setGugun(zipCodeArray.get(0).getGugun());
                }
                ((ArrayAdapter)sp_3.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((App)getActivity().getApplication()).setGugun(((ZipCode) adapterView.getSelectedItem()).getGugun());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonRefresh();
    }

    public void buttonRefresh() {
        Button[] btn = {btn_1, btn_2};
        for(int i = 0; i < btn.length; i++) {
            int rnd = new Random().nextInt(3) + 1;
            if(rnd == STATUS_FINISH) {
                long startDate = System.currentTimeMillis();
                long endDate = startDate + (3600 * 24 * 1000);
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                btn[i].setText("후기를 작성해 주세요.\n(" + format.format(startDate) + " ~ " + format.format(endDate) + ")");
                btn[i].setBackgroundColor(getResources().getColor(R.color.holo_orange_dark));
            } else if(rnd == STATUS_PROCESS) {
                btn[i].setText("서비스 이용중");
                btn[i].setBackgroundResource(R.drawable.bg_small_button_gray);
                btn[i].setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
            } else {
                btn[i].setText("신청대기");
                btn[i].setBackgroundResource(R.drawable.bg_small_button_gray);
                btn[i].setBackgroundColor(getResources().getColor(R.color.holo_red_light));
            }
        }
    }
}
