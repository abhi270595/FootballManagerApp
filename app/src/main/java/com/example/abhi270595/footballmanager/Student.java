package com.example.abhi270595.footballmanager;

import java.io.Serializable;
/**
 * Created by abhi270595 on 10/6/17.
 */

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private String emailId;

    private boolean isSelected;

    public Student() {

    }

    public Student(String name, String emailId) {

        this.name = name;
        this.emailId = emailId;

    }

    public Student(String name, String emailId, boolean isSelected) {

        this.name = name;
        this.emailId = emailId;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
