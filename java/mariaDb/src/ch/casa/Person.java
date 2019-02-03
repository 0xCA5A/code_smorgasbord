package ch.casa;

class Person {

    public final String firstName;
    public final String surName;
    public final String sex;

    Person(String firstName, String surName, String sex) {
        this.firstName = firstName.trim();
        this.surName = surName.trim();
        this.sex = sex.trim();
    }

    @Override
    public String toString() {
        return String.format("Firstname: %s, Surname: %s, Sex: %s", firstName, surName, sex);
    }

}
