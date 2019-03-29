package com.example.eslothower.collegeapp_eslothower;

public class FamilyMember {
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
}


