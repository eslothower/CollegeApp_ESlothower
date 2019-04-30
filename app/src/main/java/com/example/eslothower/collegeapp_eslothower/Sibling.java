package com.example.eslothower.collegeapp_eslothower;

public class Sibling extends FamilyMember {



    String firstName;
    String lastName;


    //A constructor for your basic sibling
    public Sibling() {

        super();
    }

    //shows your siblings name
    public String toString(){
        String result = "Sibling: " + this.getFirstName() + " " + this.getLastName();
        return result;

    }


    //sets your sibling's name
    public Sibling(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
