package com.example.eslothower.collegeapp_eslothower;

import java.util.ArrayList;

public class Family {

    private final String TAG = Family.class.getName();

    private ArrayList<FamilyMember> family;
    private static Family sFamily;

    private Family(){
        family = new ArrayList<>();
        Guardian mom = new Guardian("Shawn", "Boyer");
        Guardian dad = new Guardian("Brett", "Slothower");
        family.add(mom);
        family.add(dad);

    }

    public static Family getFamily(){
        if (sFamily == null){
            sFamily = new Family();
        }
        return sFamily;

    }

    public ArrayList<FamilyMember> getFamilyList(){
        return family;
    }

    public void setFamily(ArrayList<FamilyMember> family){
        this.family = family;
    }


}
