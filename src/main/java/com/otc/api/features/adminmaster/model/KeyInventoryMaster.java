package com.otc.api.features.adminmaster.model;


public class KeyInventoryMaster {
    private int id;
    private String keySerialNumber = "";
    private String keyType = "";
    private String keyMake = "";
    private String keyModel = "";
    private String atmId = "";
    private String imagePath = "";
    private boolean isActive = true;

    public KeyInventoryMaster() {}
    public KeyInventoryMaster(int id, String keySerialNumber, String keyType, String keyMake, String keyModel, String atmId, String imagePath, boolean isActive) {
        this.id = id;
        this.keySerialNumber = keySerialNumber;
        this.keyType = keyType;
        this.keyMake = keyMake;
        this.keyModel = keyModel;
        this.atmId = atmId;
        this.imagePath = imagePath;
        this.isActive = isActive;
    }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getKeySerialNumber() { return this.keySerialNumber; }
    public void setKeySerialNumber(String keySerialNumber) { this.keySerialNumber = keySerialNumber; }
    public String getKeyType() { return this.keyType; }
    public void setKeyType(String keyType) { this.keyType = keyType; }
    public String getKeyMake() { return this.keyMake; }
    public void setKeyMake(String keyMake) { this.keyMake = keyMake; }
    public String getKeyModel() { return this.keyModel; }
    public void setKeyModel(String keyModel) { this.keyModel = keyModel; }
    public String getAtmId() { return this.atmId; }
    public void setAtmId(String atmId) { this.atmId = atmId; }
    public String getImagePath() { return this.imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public boolean isIsActive() { return this.isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
