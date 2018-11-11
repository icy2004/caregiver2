package com.example.caregiver.model;

public class ZipCode {
    private String sido;
    private String gugun;

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

    @Override
    public String toString() {
        return gugun;
    }
}
