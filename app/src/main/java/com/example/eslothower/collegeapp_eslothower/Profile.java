package com.example.eslothower.collegeapp_eslothower;

import java.util.Date;

public class Profile extends ApplicantData {
    String mLastName;
    String mFirstName;
    Date dateOfBirth;
    private final static String  photoFileName = "IMG_PROFILE.jpg";

    public Date getDateOfBirth() {
        return dateOfBirth;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhotoFilename(){
        return photoFileName;
    }



    /*public Profile() {
        dateOfBirth = new Date();
    }*/



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
        dateOfBirth = new Date();

    }

}
