package com.example.eslothower.collegeapp_eslothower;

public class Guardian extends FamilyMember{

    String firstName;
    String lastName;
    String occupation;

    public Guardian(String firstName) {
        this.firstName = firstName;
    }


    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }



    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public Guardian(){
        super();
        this.occupation = "unknown";

    }

    public Guardian(String firstName, String lastName){
        super(firstName, lastName);
        this.occupation = "unknown";

    }

    public Guardian(String firstName, String lastName, String occupation) {
        super(firstName, lastName);
        this.occupation = occupation;
    }

    public String toString(){
        String result = "Guardian: " + this.getFirstName() + " " + this.getLastName() + "\n\nOccupation: " + this.occupation;
        return result;

    }

}
