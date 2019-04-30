package com.example.eslothower.collegeapp_eslothower;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.util.Date;



//A bunch of getters and setters for each piece of information regarding your profile, like name, DOB, etc. Also contructors for your profile.

public class Profile extends ApplicantData {
    String mLastName;
    String mFirstName;

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

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


    //method for retrieving your photo you took in the app
    public File getPhotoFile(Context context){
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, getPhotoFilename());
    }

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


    //default profile
    public Profile(){
        mFirstName = "Alan";
        mLastName = "Turing";
        dateOfBirth = new Date();

    }

}
