package com.example.eslothower.collegeapp_eslothower;

import java.util.ArrayList;

public class Family {

    private final String TAG = Family.class.getName();

    private ArrayList<FamilyMember> family;
    private static Family sFamily;


    //adds family members
    private Family(){
        family = new ArrayList<>();
        Guardian mom = new Guardian("Shawn", "Boyer");
        Guardian dad = new Guardian("Brett", "Slothower");
        Sibling zoe = new Sibling("Zoe", "Slothower");
        family.add(mom);
        family.add(dad);
        family.add(zoe);

    }

    public static Family getFamily(){
        if (sFamily == null){
            sFamily = new Family();
        }
        return sFamily;

    }


    //method for adding a family members
    public void addFamilyMember(FamilyMember fm){
        family.add(fm);
    }


    //method for deleting family members
    public void deleteFamilyMember(FamilyMember fm){
        family.remove(fm);
    }


    //array list of family members
    public ArrayList<FamilyMember> getFamilyList(){
        return family;
    }

    //sets family members to the array list
    public void setFamily(ArrayList<FamilyMember> family){
        this.family = family;
    }


}
