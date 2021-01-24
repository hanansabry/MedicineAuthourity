package com.app.medicineauthourity.data.model;

import com.app.medicineauthourity.R;

public class Medicine {

    public enum Composition {
        Simple , Complex;
    }

    public enum Usage {
        External, Internal
    }

    public enum Physiological {
        Special, General, Prolonged
    }

    public enum Production {
        Original, License, Generic
    }

    private String id;
    private String name;
    private String barQRCode;
    private String categoryName;
    private String composition;
    private String usage;
    private String physiological;
    private String production;
    private boolean approved;

    public Medicine(String name, String barQRCode, String categoryName, String composition, String usage, String physiological, String production) {
        this.name = name;
        this.barQRCode = barQRCode;
        this.categoryName = categoryName;
        this.composition = composition;
        this.usage = usage;
        this.physiological = physiological;
        this.production = production;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarQRCode() {
        return barQRCode;
    }

    public void setBarQRCode(String barQRCode) {
        this.barQRCode = barQRCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getPhysiological() {
        return physiological;
    }

    public void setPhysiological(String physiological) {
        this.physiological = physiological;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
