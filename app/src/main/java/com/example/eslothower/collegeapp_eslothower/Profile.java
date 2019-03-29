package com.example.eslothower.collegeapp_eslothower;

public class Profile {
    String mLastName;
    String mFirstName;

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

    public Profile(){
        mFirstName = "Alan";
        mLastName = "Turing";

    }

}
