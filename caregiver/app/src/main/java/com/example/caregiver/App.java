package com.example.caregiver;

import android.app.Activity;
import android.app.Application;
import android.inputmethodservice.Keyboard;
import android.text.TextUtils;
import android.util.Log;

import com.example.caregiver.activity.LoginActivity;
import com.example.caregiver.model.Caregiver;
import com.example.caregiver.model.ZipCode;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class App extends Application{

    private LinkedHashSet<String> sidoArray;
    private List<ZipCode> zipCodeArray;
    private List<Caregiver> caregiverArray;
    private String sido = "서울";
    private String gugun = "강남구";
    private String disease = "뇌출혈";

    @Override
    public void onCreate() {
        super.onCreate();

        sidoArray = new LinkedHashSet<>();
        zipCodeArray = new ArrayList<>();
        caregiverArray = new ArrayList<>();
    }

    public void load(final LoginActivity activity) {
        new Thread() {
            @Override
            public void run() {
                BufferedReader br = null;
                try {
                    InputStream is = getAssets().open("zipcode");
                    br = new BufferedReader(new InputStreamReader(is));
                    String str = null;
                    while((str = br.readLine()) != null) {
                        String[] strs = str.split(",");
                        sidoArray.add(strs[1]);
                        if(!exist(strs[1], strs[2])) {
                            ZipCode zipCode = new ZipCode();
                            zipCode.setSido(strs[1]);
                            zipCode.setGugun(strs[2]);
                            zipCodeArray.add(zipCode);
                        }
                    }
                    excelRead();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {}
                    }

                    activity.loadFinish();
                }
            }
        }.start();
    }

    public void excelRead() {
        BufferedReader br = null;
        try {
            InputStream is = getAssets().open("help");
            br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            while((str = br.readLine()) != null) {
                String[] strs = str.split(",");
                int seq = Integer.parseInt(strs[0]);
                String value1 = strs[1];
                String value2 = strs[2];
                int value3 = Integer.parseInt(strs[3]);
                String value4 = strs[4];
                int value5 = Integer.parseInt(strs[5]);
                String value6 = strs[6];
                float value7 = Float.parseFloat(strs[7]);
                String value8 = strs[8];
                int value9 = Integer.parseInt(strs[9]);
                Caregiver caregiver = new Caregiver();
                caregiver.setSeq(seq);
                caregiver.setName(value1);
                caregiver.setGender(value2);
                caregiver.setAge(value3);
                caregiver.setPro(value4);
                caregiver.setExp(value5);
                caregiver.setEtc(value6);
                caregiver.setAvg(value7);
                caregiver.setLocale(value8);
                caregiver.setPay(value9);
                caregiverArray.add(caregiver);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {}
            }
        }
    }

    protected String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("dd/MM/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {
        }
        return value;
    }

    private boolean exist(String sido, String gugun) {
        for(ZipCode zipCode : zipCodeArray) {
            if(TextUtils.equals(zipCode.getSido(), sido) && TextUtils.equals(zipCode.getGugun(), gugun)) {
                return true;
            }
        }
        return false;
    }

    public LinkedHashSet<String> getSidoArray() {
        return sidoArray;
    }

    public void setSidoArray(LinkedHashSet<String> sidoArray) {
        this.sidoArray = sidoArray;
    }

    public List<ZipCode> getZipCodeArray() {
        return zipCodeArray;
    }

    public void setZipCodeArray(List<ZipCode> zipCodeArry) {
        this.zipCodeArray = zipCodeArry;
    }

    public List<Caregiver> getCaregiverArray() {
        return caregiverArray;
    }

    public void setCaregiverArray(List<Caregiver> caregiverArray) {
        this.caregiverArray = caregiverArray;
    }

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getGugun() {
        return gugun;
    }

    public void setGugun(String gugun) {
        this.gugun = gugun;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
