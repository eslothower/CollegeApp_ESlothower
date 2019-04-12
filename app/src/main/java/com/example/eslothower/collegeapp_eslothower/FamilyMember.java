package com.example.eslothower.collegeapp_eslothower;

public class FamilyMember extends ApplicantData {
    String mFirstName;



    String mLastName;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public FamilyMember(){
        mFirstName = "Ada";
        mLastName = "Lovelace";

    }
    public FamilyMember(String firstName, String lastName){
        mFirstName = firstName;
        mLastName = lastName;
    }
}


