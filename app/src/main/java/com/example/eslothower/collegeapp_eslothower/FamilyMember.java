package com.example.eslothower.collegeapp_eslothower;

public class FamilyMember extends ApplicantData {
    String mFirstName;
    String mLastName;

    public static final String EXTRA_RELATION = "org.pltw.examples.collegeapp.relation";
    public static final String EXTRA_INDEX = "org.pltw.examples.collegeapp.index";

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

    public boolean equals(Object o) {
        if ((o instanceof Guardian) && (this instanceof Guardian)) {
            // both are guardians so cast the Object
            Guardian g = (Guardian)o;
            // test for equality of first and last names for g and this
            if (g.firstName == ((Guardian) this).firstName && g.lastName == ((Guardian) this).lastName){
                return true;
            }

            return false;
        } else {
            // both are guardians so cast the Object
            Sibling s = (Sibling)o;
            // test for equality of first and last names for g and this
            if (s.firstName == ((Sibling) this).firstName && s.lastName == ((Sibling) this).lastName){
                return true;
            }

            return false;
        }
    }
}


