package com.otc.api.features.adminmaster.model;


public class OneLineMaster {
    private int id;
    private String name = "";
    private String description = "";
    private String contactNumber = "";
    private String email = "";
    private boolean isActive = true;

    public OneLineMaster() {}
    public OneLineMaster(int id, String name, String description, String contactNumber, String email, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contactNumber = contactNumber;
        this.email = email;
        this.isActive = isActive;
    }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
    public String getContactNumber() { return this.contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isIsActive() { return this.isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
