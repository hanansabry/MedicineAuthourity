package com.app.medicineauthourity.data.model;

public class Medicine {

    public enum Composition {
        Simple, Complex
    }

    public enum Usage {
        External, Internal
    }

    public enum Physiological {
        External, Internal
    }

    public enum Production {
        Original, License, Generic
    }

    private String id;
    private String name;
    private String barQRCode;
    private String categoryId;
    private String composition;
    private String usage;
    private String physiological;
    private String production;
    private boolean approved;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
