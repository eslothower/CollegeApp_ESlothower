package com.example.eslothower.collegeapp_eslothower;

public class Sibling extends FamilyMember {



    String firstName;
    String lastName;

    public Sibling() {

        super();
    }

    public String toString(){
        String result = "Sibling: " + this.getFirstName() + " " + this.getLastName();
        return result;

    }

    public Sibling(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
