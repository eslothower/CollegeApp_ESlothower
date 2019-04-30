package com.example.eslothower.collegeapp_eslothower;

public class Guardian extends FamilyMember{

    String firstName;
    String lastName;
    String occupation;

    //A bunch of getters and setters specifically for your guardian, like name and occupation

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


    //These set the email, names, occupation
    public Guardian(){
        super();
        this.occupation = "unknown";
        super.setEmail("jlinburg@doversd.org");

    }

    public Guardian(String firstName, String lastName){
        super(firstName, lastName);
        this.occupation = "unknown";
        super.setEmail("jlinburg@doversd.org");

    }

    public Guardian(String firstName, String lastName, String occupation) {
        super(firstName, lastName);
        this.occupation = occupation;
        super.setEmail("jlinburg@doversd.org");
    }


    public Guardian(String firstName, String lastName, String occupation, String email) {
        super(firstName, lastName);
        this.occupation = occupation;
        super.setEmail("jlinburg@doversd.org");
    }


    //Displays your guarian and their info: name and occupation, as a string
    public String toString(){
        String result = "Guardian: " + this.getFirstName() + " " + this.getLastName() + "\n\nOccupation: " + this.occupation;
        return result;

    }

}
